package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.*;
import ru.kpfu.itis.lobanov.data.services.AccountService;
import ru.kpfu.itis.lobanov.data.services.CurrencyConverterService;
import ru.kpfu.itis.lobanov.data.services.TransactionService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;
import ru.kpfu.itis.lobanov.exceptions.TransactionTypeNotFoundException;
import ru.kpfu.itis.lobanov.utils.AccountNumberGenerator;
import ru.kpfu.itis.lobanov.utils.DateProvider;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.kpfu.itis.lobanov.utils.BankingConstants.SAVINGS_ACCOUNT_PERCENTAGE;
import static ru.kpfu.itis.lobanov.utils.ExceptionMessages.NO_SUCH_TRANSACTION_TYPE;
import static ru.kpfu.itis.lobanov.utils.NamingConstants.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final AccountTypeRepository accountTypeRepository;
    private final CurrencyRepository currencyRepository;
    private final UserRepository userRepository;
    private final TransactionRepository transactionRepository;
    private final TransactionService transactionService;
    private final CurrencyConverterService currencyConverterService;
    private final Mapper<Account, BankAccountDto> bankAccountMapper;
    private final DateProvider dateProvider;
    private final AccountNumberGenerator numberGenerator;

    @Override
    public List<BankAccountDto> getAllAccounts() {
        return bankAccountMapper.toListResponse(accountRepository.findAll());
    }

    @Override
    public List<BankAccountDto> getAllUserAccounts(Long userId) {
        return bankAccountMapper.toListResponse(accountRepository.findAllByOwnerId(userId));
    }

    @Override
    public List<BankAccountDto> getAllUserCardAccounts(Long userId) {
        return bankAccountMapper.toListResponse(accountRepository.findAllCardsByOwnerId(userId));
    }

    @Override
    public BankAccountDto createAccount(CreateAccountRequest request) {
        AccountType type = accountTypeRepository.findById(Long.parseLong(request.getTypeId())).orElseThrow(IllegalArgumentException::new);
        Currency currency = currencyRepository.findById(Long.parseLong(request.getCurrencyId())).orElseThrow(IllegalArgumentException::new);
        User owner = userRepository.findById(Long.parseLong(request.getOwnerId())).orElseThrow(IllegalArgumentException::new);

        Account account = Account.builder()
                .name(request.getName())
                .owner(owner)
                .type(type)
                .currency(currency)
                .deposit(BigDecimal.ZERO)
                .number(numberGenerator.generatePersonalAccountNumber())
                .build();
        return bankAccountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public void closeAccount(CloseAccountRequest request) {
        Account account = accountRepository.findById(Long.parseLong(request.getAccountId())).orElseThrow(IllegalArgumentException::new);
        accountRepository.delete(account);
    }

    @Override
    public BankAccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return bankAccountMapper.toResponse(account);
    }

    @Override
    public BankAccountDto getUserMainAccount(Long userId) {
        return bankAccountMapper.toResponse(accountRepository.findUserMainAccount(userId).orElse(null));
    }

    @Override
    public BankAccountDto updateAccountName(Long id, String name) {
        accountRepository.updateNameById(id, name);
        Account account = accountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return bankAccountMapper.toResponse(account);
    }

    @Override
    public BankAccountDto bindCard(BindCardRequest request) {
        Account account = accountRepository.findById(Long.parseLong(request.getAccountId())).orElseThrow(IllegalArgumentException::new);
        Card card = cardRepository.findById(Long.parseLong(request.getCardId())).orElseThrow(IllegalArgumentException::new);
        account.getCards().add(card);
        return bankAccountMapper.toResponse(accountRepository.save(account));
    }

    @Override
    public AccountStatementDto getStatement(Long accountId, String date) {
        Account account = accountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);

        LocalDate dateFrom = dateProvider.getFullDate(date);
        LocalDate dateTo = dateProvider.getNextMonth(date);

        BigDecimal lastMonthDeposit = transactionRepository.getBeginningMonthDeposit(account, dateFrom);
        if (lastMonthDeposit == null) lastMonthDeposit = BigDecimal.ZERO;
        lastMonthDeposit = currencyConverterService.convert(
                account.getCurrency().getIsoCode3(),
                CURRENCY_RUBLES_ISO_CODE_3,
                lastMonthDeposit
        );

        List<Transaction> replenishmentTransactions = transactionRepository.findAllByAccountToAndBetweenDates(account, dateFrom, dateTo);
        List<Transaction> transferTransactions = transactionRepository.findAllByAccountFromAndBetweenDates(account, dateFrom, dateTo);

        BigDecimal replenishment = currencyConverterService.convert(
                account.getCurrency().getIsoCode3(),
                CURRENCY_RUBLES_ISO_CODE_3,
                replenishmentTransactions.stream().map(Transaction::getInitAmount).reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        BigDecimal transfer = currencyConverterService.convert(
                account.getCurrency().getIsoCode3(),
                CURRENCY_RUBLES_ISO_CODE_3,
                transferTransactions.stream().map(Transaction::getTotalAmount).reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        BigDecimal newDeposit = lastMonthDeposit.add(replenishment).subtract(transfer);

        BigDecimal cashback = currencyConverterService.convert(
                account.getCurrency().getIsoCode3(),
                CURRENCY_RUBLES_ISO_CODE_3,
                transactionRepository.getCashbackBetweenDates(accountId, dateFrom, dateTo)
        );

        BigDecimal interest;
        switch (account.getType().getName()) {
            case ACCOUNT_TYPE_PERSONAL:
                interest = BigDecimal.ZERO;
                break;
            case ACCOUNT_TYPE_SAVINGS:
                interest = account.getTransactions().stream().filter(transaction ->
                                transaction.getType().getName().equals(TRANSACTION_TYPE_INTEREST))
                        .map(Transaction::getTotalAmount)
                        .reduce(BigDecimal.ZERO, BigDecimal::add);
                break;
            default:
                throw new TransactionTypeNotFoundException(NO_SUCH_TRANSACTION_TYPE);
        }

        return AccountStatementDto.builder()
                .startBalance(lastMonthDeposit)
                .receipts(replenishment)
                .interest(interest)
                .cashback(cashback)
                .expenses(transfer)
                .endBalance(newDeposit)
                .build();
    }

    @Override
    @Scheduled(cron = "@monthly")
    @SchedulerLock(name = SCHEDULER_LOCK_CHARGE_INTEREST_NAME)
    public void chargeInterestOnAccounts() {
        Optional<AccountType> typeOptional = accountTypeRepository.findByName(ACCOUNT_TYPE_SAVINGS);
        if (typeOptional.isPresent()) {
            List<Account> accounts = accountRepository.findAllByType(typeOptional.get());
            for (Account account : accounts) {
                BigDecimal deposit = account.getDeposit();
                transactionService.chargeInterestTransaction(account, deposit.multiply(SAVINGS_ACCOUNT_PERCENTAGE));
            }
        }
    }
}
