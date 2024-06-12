package ru.kpfu.itis.lobanov.data.services.impl;

import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.*;
import ru.kpfu.itis.lobanov.data.services.CurrencyService;
import ru.kpfu.itis.lobanov.data.services.MessagingService;
import ru.kpfu.itis.lobanov.data.services.TransactionService;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.forms.*;
import ru.kpfu.itis.lobanov.exceptions.*;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

import static ru.kpfu.itis.lobanov.utils.ExceptionMessages.*;
import static ru.kpfu.itis.lobanov.utils.NamingConstants.*;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionTypeRepository transactionTypeRepository;
    private final TransactionMethodRepository transactionMethodRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final CurrencyService currencyService;
    private final MessagingService messagingService;
    private final Mapper<Transaction, TransactionDto> transactionMapper;

    @Override
    public TransactionDto getById(Long transactionId) {
        return transactionMapper.toResponse(transactionRepository.findById(transactionId).orElse(null));
    }

    @Override
    public List<TransactionDto> getAllOperationsFromUser(UserDto userDto) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUserExpenses(userDto.getId()));
    }

    @Override
    public List<TransactionDto> getAllRecentTransactions(Long accountId) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUserLimitRecent(accountId));
    }

    @Override
    public List<TransactionDto> getAllTransactionsFromUserExpenses(Long userId) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUserExpenses(userId));
    }

    @Override
    public List<TransactionDto> getAllTransactionsFromUserReceipts(Long userId) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUserReceipts(userId));
    }

    @Override
    public TransactionDto transferByPhone(PhoneTransferForm phoneTransferForm) {
        Account currentAccount = accountRepository.findById(phoneTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        List<Transaction> transactions = currentAccount.getTransactions();
        BigDecimal deposit = BigDecimal.ZERO;
        if (transactions != null && !transactions.isEmpty()) {
            BigDecimal transfer = BigDecimal.ZERO;
            BigDecimal replenishment = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                if (Objects.equals(transaction.getFrom(), currentAccount.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        transfer = transfer.add(transaction.getInitAmount());
                    } else {
                        transfer = transfer.add(transaction.getTotalAmount());
                    }
                } else if (Objects.equals(transaction.getTo(), currentAccount.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        replenishment = replenishment.add(transaction.getInitAmount());
                    } else {
                        replenishment = replenishment.add(transaction.getTotalAmount());
                    }
                }
            }
            deposit = replenishment.subtract(transfer);
        }

        if (deposit.compareTo(phoneTransferForm.getAmount()) < 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        User anotherUser = userRepository.findByPhone(phoneTransferForm.getPhone()).orElse(null);
        if (anotherUser == null) {
            anotherUser = userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new);
        }
        Account anotherAccount = accountRepository.findAllByOwnerId(anotherUser.getId()).get(0);
        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Transaction transaction = createTransaction(
                currentAccount,
                anotherAccount,
                phoneTransferForm.getAmount(),
                method,
                BANK_NAME,
                BANK_NAME,
                null,
                BANK_NAME,
                phoneTransferForm.getMessage()
        );
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionDto transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm) {
        Account accountToTransferFrom = accountRepository.findById(betweenAccountsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        List<Transaction> transactions = accountToTransferFrom.getTransactions();
        BigDecimal deposit = BigDecimal.ZERO;
        if (transactions != null && !transactions.isEmpty()) {
            BigDecimal transfer = BigDecimal.ZERO;
            BigDecimal replenishment = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                if (Objects.equals(transaction.getFrom(), accountToTransferFrom.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        transfer = transfer.add(transaction.getInitAmount());
                    } else {
                        transfer = transfer.add(transaction.getTotalAmount());
                    }
                } else if (Objects.equals(transaction.getTo(), accountToTransferFrom.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        replenishment = replenishment.add(transaction.getInitAmount());
                    } else {
                        replenishment = replenishment.add(transaction.getTotalAmount());
                    }
                }
            }
            deposit = replenishment.subtract(transfer);
        }

        if (deposit.compareTo(betweenAccountsTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        Account accountToTransferTo = accountRepository.findById(betweenAccountsTransferForm.getTo()).orElseThrow(IllegalArgumentException::new);
        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Transaction transaction = createTransaction(
                accountToTransferFrom,
                accountToTransferTo,
                betweenAccountsTransferForm.getAmount(),
                method,
                BANK_NAME,
                BANK_NAME,
                null,
                BANK_NAME,
                null);
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionDto transferByCard(CardTransferForm cardTransferForm) {
        Account currentAccount = accountRepository.findById(cardTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        List<Transaction> transactions = currentAccount.getTransactions();
        BigDecimal deposit = BigDecimal.ZERO;
        if (transactions != null && !transactions.isEmpty()) {
            BigDecimal transfer = BigDecimal.ZERO;
            BigDecimal replenishment = BigDecimal.ZERO;
            for (Transaction transaction : transactions) {
                if (Objects.equals(transaction.getFrom(), currentAccount.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        transfer = transfer.add(transaction.getInitAmount());
                    } else {
                        transfer = transfer.add(transaction.getTotalAmount());
                    }
                } else if (Objects.equals(transaction.getTo(), currentAccount.getId())) {
                    if (transaction.getTotalAmount() == null) {
                        replenishment = replenishment.add(transaction.getInitAmount());
                    } else {
                        replenishment = replenishment.add(transaction.getTotalAmount());
                    }
                }
            }
            deposit = replenishment.subtract(transfer);
        }

        if (deposit.compareTo(cardTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }
        Account anotherAccount = accountRepository.findByCardNumber(cardTransferForm.getCard()).orElse(null);
        if (anotherAccount == null) {
            anotherAccount = accountRepository.findAllByOwnerId(
                    userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new).getId()
            ).get(0);
        }
//        Account anotherAccount = accountRepository.findByCardNumber(cardTransferForm.getCard())
//                .orElse(accountRepository.findAllByOwnerId(
//                        userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new).getId()
//                ).get(0));
        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Transaction transaction = createTransaction(
                currentAccount,
                anotherAccount,
                cardTransferForm.getAmount(),
                method,
                BANK_NAME,
                BANK_NAME,
                null,
                BANK_NAME,
                cardTransferForm.getMessage());
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionDto transferByAccountDetails(AccountDetailsTransferForm accountDetailsTransferForm) {
        Account currentAccount = accountRepository.findById(accountDetailsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(accountDetailsTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        Account anotherAccount = accountRepository.findByContractNumber(accountDetailsTransferForm.getContractNumber())
                .orElse(accountRepository.findAllByOwnerId(
                        userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new).getId()
                ).get(0));
        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Transaction transaction = createTransaction(
                currentAccount,
                anotherAccount,
                accountDetailsTransferForm.getAmount(),
                method,
                BANK_NAME,
                BANK_NAME,
                null,
                BANK_NAME,
                accountDetailsTransferForm.getMessage());
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionDto replenishByCard(CardReplenishForm cardReplenishForm) {
        Account currentAccount = accountRepository.findByCardNumber(cardReplenishForm.getCard()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(cardReplenishForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        Account anotherAccount = accountRepository.findById(cardReplenishForm.getTo())
                .orElse(accountRepository.findAllByOwnerId(
                        userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new).getId()
                ).get(0));

        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Transaction transaction = createTransaction(
                currentAccount,
                anotherAccount,
                cardReplenishForm.getAmount(),
                method,
                BANK_NAME,
                BANK_NAME,
                null,
                BANK_NAME,
                null);
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public void chargeInterestTransaction(Account account, BigDecimal amount) {
        final LocalDateTime now = LocalDateTime.now();
        final Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        TransactionType type = transactionTypeRepository.findByName(TRANSACTION_TYPE_INTEREST).orElseThrow(
                () -> new TransactionTypeNotFoundException(NO_SUCH_TRANSACTION_TYPE)
        );
        TransactionMethod method = transactionMethodRepository.findByName(TRANSACTION_METHOD_TRANSFER)
                .orElseThrow(() -> new TransactionMethodNotFoundException(NO_SUCH_TRANSACTION_METHOD));

        Account adminAccount = accountRepository.findAllByOwnerId(
                userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new).getId()
        ).get(0);

        Transaction transaction = Transaction.builder()
                .date(Timestamp.from(instant))
                .initAmount(amount)
                .currency(account.getCurrency())
                .type(type)
                .method(method)
                .from(adminAccount.getId())
                .to(account.getId())
                .bankNameFrom(BANK_NAME)
                .bankNameTo(BANK_NAME)
                .terminalIp(null)
                .serviceCompany(BANK_NAME)
                .message(null)
                .commission(BigDecimal.ZERO)
                .cashback(BigDecimal.ZERO)
                .riskIndicator(0)
                .totalAmount(amount)
                .build();

        transaction = transactionRepository.save(transaction);

//        account.setDeposit(account.getDeposit().add(amount));  //TODO

        account.getTransactions().add(transaction);
        adminAccount.getTransactions().add(transaction);

        accountRepository.save(account);
        accountRepository.save(adminAccount);
    }

    private Transaction createTransaction(
            @NotNull
            Account currentAccount,
            @NotNull
            Account anotherAccount,
            @NotNull
            BigDecimal amount,
            @NotNull
            TransactionMethod method,
            @NotNull
            String bankNameFrom,
            @NotNull
            String bankNameTo,
            String terminalIp,
            String serviceCompany,
            String message
    ) {
        validateTransactionCreation(amount, bankNameFrom, bankNameTo);

        final LocalDateTime now = LocalDateTime.now();
        final Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        Random random = new Random(System.currentTimeMillis());
        Category category = categoryRepository.findById((long) (1 + random.nextInt(34))).orElseThrow(IllegalArgumentException::new);

        Optional<TransactionType> optionalType = transactionTypeRepository.findByName(TRANSACTION_TYPE_PENDING);
        if (optionalType.isEmpty()) throw new TransactionTypeNotFoundException(NO_SUCH_TRANSACTION_TYPE);

        Currency currencyFrom = currentAccount.getCurrency();
        Currency currencyTo = anotherAccount.getCurrency();

        BigDecimal convertedAmount = currencyService.convert(currencyFrom.getIsoCode3(), currencyTo.getIsoCode3(), amount);

        Transaction transaction = Transaction.builder()
                .date(Timestamp.from(instant))
                .initAmount(convertedAmount)
                .currency(currencyTo)
                .type(optionalType.get())
                .method(method)
                .from(currentAccount.getId())
                .to(anotherAccount.getId())
                .bankNameFrom(bankNameFrom)
                .bankNameTo(bankNameTo)
                .terminalIp(terminalIp)
                .serviceCompany(serviceCompany)
                .category(category)
                .message(message)
                .build();

        transaction = transactionRepository.save(transaction);
        messagingService.sendTransactionToChargeCommission(transaction);

//        currentAccount.setDeposit(currentAccount.getDeposit().subtract(amount)); // TODO
//        anotherAccount.setDeposit(anotherAccount.getDeposit().add(convertedAmount));
        currentAccount.getTransactions().add(transaction);
        anotherAccount.getTransactions().add(transaction);
        accountRepository.save(currentAccount);
        accountRepository.save(anotherAccount);
        return transaction;
    }

    private void validateTransactionCreation(
            BigDecimal amount,
            String bankNameFrom,
            String bankNameTo
    ) {
        if (amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new InvalidAmountOfTransactionException(INVALID_AMOUNT_OF_MONEY);
        if (bankNameFrom.isBlank())
            throw new InvalidBinException(INVALID_SENDER_BANK_NAME);
        if (bankNameTo.isBlank())
            throw new InvalidBinException(INVALID_RECEIVER_BANK_NAME);
    }
}
