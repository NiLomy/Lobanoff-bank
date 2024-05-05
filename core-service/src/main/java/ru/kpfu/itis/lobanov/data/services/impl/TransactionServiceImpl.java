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
    private final BankAccountRepository bankAccountRepository;
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
        BankAccount currentAccount = bankAccountRepository.findById(phoneTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(phoneTransferForm.getAmount()) < 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        User anotherUser = userRepository.findByPhone(phoneTransferForm.getPhone())
                .orElse(userRepository.findByEmail(ADMIN_EMAIL).orElseThrow(IllegalArgumentException::new));
        BankAccount anotherAccount = bankAccountRepository.findAllByOwnerId(anotherUser.getId()).get(0);
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
        BankAccount accountToTransferFrom = bankAccountRepository.findById(betweenAccountsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (accountToTransferFrom.getDeposit().compareTo(betweenAccountsTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        BankAccount accountToTransferTo = bankAccountRepository.findById(betweenAccountsTransferForm.getTo()).orElseThrow(IllegalArgumentException::new);
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
        BankAccount currentAccount = bankAccountRepository.findById(cardTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(cardTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        BankAccount anotherAccount = bankAccountRepository.findByCardNumber(cardTransferForm.getCard())
                .orElse(bankAccountRepository.findAllByOwnerId(
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
        BankAccount currentAccount = bankAccountRepository.findById(accountDetailsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(accountDetailsTransferForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }
        // TODO implement contracts
        BankAccount anotherAccount = bankAccountRepository.findByContractNumber(accountDetailsTransferForm.getContractNumber())
                .orElse(bankAccountRepository.findAllByOwnerId(
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
        BankAccount currentAccount = bankAccountRepository.findByCardNumber(cardReplenishForm.getCard()).orElseThrow(IllegalArgumentException::new);

        if (currentAccount.getDeposit().compareTo(cardReplenishForm.getAmount()) <= 0) {
            throw new NotEnoughMoneyException(NOT_ENOUGH_MONEY_FOR_OPERATION);
        }

        BankAccount anotherAccount = bankAccountRepository.findById(cardReplenishForm.getTo())
                .orElse(bankAccountRepository.findAllByOwnerId(
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

    private Transaction createTransaction(
            @NonNull
            BankAccount currentAccount,
            @NonNull
            BankAccount anotherAccount,
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
