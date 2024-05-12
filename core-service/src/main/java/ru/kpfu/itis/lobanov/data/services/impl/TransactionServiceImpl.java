package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.*;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.*;
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
import java.util.Optional;

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
    private final UserRepository userRepository;
    private final MessagingService messagingService;
    private final Mapper<Transaction, TransactionDto> transactionMapper;

    @Override
    public TransactionDto getById(@NonNull Long transactionId) {
        return transactionMapper.toResponse(transactionRepository.findById(transactionId).orElse(null));
    }

    @Override
    public List<TransactionDto> getAllOperationsFromUser(@NonNull UserDto userDto) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUser(userDto.getId()));
    }

    @Override
    public List<TransactionDto> getAllRecentTransactions(@NonNull Long accountId) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUserLimitRecent(accountId));
    }

    @Override
    public TransactionDto transferByPhone(@NonNull PhoneTransferForm phoneTransferForm) {
        Account currentAccount = accountRepository.findById(phoneTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(phoneTransferForm.getAmount()) < 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        User anotherUser = userRepository.findByPhone(phoneTransferForm.getPhone())
                .orElse(userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new));
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
    public TransactionDto transferBetweenAccounts(@NonNull BetweenAccountsTransferForm betweenAccountsTransferForm) {
        Account accountToTransferFrom = accountRepository.findById(betweenAccountsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (accountToTransferFrom.getDeposit().compareTo(betweenAccountsTransferForm.getAmount()) <= 0) {
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
    public TransactionDto transferByCard(@NonNull CardTransferForm cardTransferForm) {
        Account currentAccount = accountRepository.findById(cardTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(cardTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        Account anotherAccount = accountRepository.findByCardNumber(cardTransferForm.getCard())
                .orElse(accountRepository.findAllByOwnerId(
                        userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new).getId()
                ).get(0));
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
                null);
        return transactionMapper.toResponse(transaction);
    }

    @Override
    public TransactionDto transferByAccountDetails(@NonNull AccountDetailsTransferForm accountDetailsTransferForm) {
        Account currentAccount = accountRepository.findById(accountDetailsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(accountDetailsTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }
        // TODO implement contracts
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
    public TransactionDto replenishByCard(@NonNull CardReplenishForm cardReplenishForm) {
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

        account.setDeposit(account.getDeposit().add(amount));

        account.getTransactions().add(transaction);
        adminAccount.getTransactions().add(transaction);

        accountRepository.save(account);
        accountRepository.save(adminAccount);
    }

    private Transaction createTransaction(
            @NonNull
            Account currentAccount,
            @NonNull
            Account anotherAccount,
            @NonNull
            BigDecimal amount,
            @NonNull
            TransactionMethod method,
            @NonNull
            String bankNameFrom,
            @NonNull
            String bankNameTo,
            String terminalIp,
            String serviceCompany,
            String message
    ) {
        validateTransactionCreation(amount, bankNameFrom, bankNameTo);

        final LocalDateTime now = LocalDateTime.now();
        final Instant instant = now.atZone(ZoneId.systemDefault()).toInstant();

        Optional<TransactionType> optionalType = transactionTypeRepository.findByName(TRANSACTION_TYPE_PENDING);
        if (optionalType.isEmpty()) throw new TransactionTypeNotFoundException(NO_SUCH_TRANSACTION_TYPE);

        Transaction transaction = Transaction.builder()
                .date(Timestamp.from(instant))
                .initAmount(amount)
                .currency(anotherAccount.getCurrency())
                .type(optionalType.get())
                .method(method)
                .from(currentAccount.getId())
                .to(anotherAccount.getId())
                .bankNameFrom(bankNameFrom)
                .bankNameTo(bankNameTo)
                .terminalIp(terminalIp)
                .serviceCompany(serviceCompany)
                .message(message)
                .build();

        transaction = transactionRepository.save(transaction);
        messagingService.sendTransactionToChargeCommission(transaction);

        // TODO make this logic in final stage of transaction processing
        currentAccount.setDeposit(currentAccount.getDeposit().subtract(amount));
        anotherAccount.setDeposit(anotherAccount.getDeposit().add(amount));
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
