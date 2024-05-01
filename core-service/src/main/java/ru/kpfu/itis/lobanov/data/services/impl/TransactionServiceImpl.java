package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.TransactionRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.TransactionService;
import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    private final TransactionRepository transactionRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final Mapper<Operation, TransactionDto> transactionMapper;

    @Override
    public TransactionDto getById(Long transactionId) {
        return transactionMapper.toResponse(transactionRepository.findById(transactionId).orElse(null));
    }

    @Override
    public List<TransactionDto> getAllOperationsFromUser(UserDto userDto) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUser(userDto.getId()));
    }

    @Override
    public List<TransactionDto> getAllRecentTransactions(Long accountId) {
        return transactionMapper.toListResponse(transactionRepository.findAllByUserLimitRecent(accountId));
    }

    @Override
    public TransactionDto transferByPhone(PhoneTransferForm phoneTransferForm) {
        BankAccount currentAccount = bankAccountRepository.findById(phoneTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (currentAccount.getDeposit() < phoneTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        User anotherUser = userRepository.findByPhone(phoneTransferForm.getPhone());
        BankAccount anotherAccount = bankAccountRepository.findAllByOwnerId(anotherUser.getId()).get(0);
        Operation operation = createTransaction(currentAccount, anotherAccount, phoneTransferForm.getAmount(), phoneTransferForm.getMessage());
        return transactionMapper.toResponse(operation);
    }

    @Override
    public TransactionDto transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm) {
        BankAccount accountToTransferFrom = bankAccountRepository.findById(betweenAccountsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (accountToTransferFrom.getDeposit() < betweenAccountsTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        BankAccount accountToTransferTo = bankAccountRepository.findById(betweenAccountsTransferForm.getTo()).orElseThrow(IllegalArgumentException::new);
        Operation operation = createTransaction(accountToTransferFrom, accountToTransferTo, betweenAccountsTransferForm.getAmount(), null);
        return transactionMapper.toResponse(operation);
    }

    @Override
    public TransactionDto transferByCard(CardTransferForm cardTransferForm) {
        BankAccount currentAccount = bankAccountRepository.findById(cardTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (currentAccount.getDeposit() < cardTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        BankAccount anotherAccount = bankAccountRepository.findByCardNumber(cardTransferForm.getCard());
        Operation operation = createTransaction(currentAccount, anotherAccount, cardTransferForm.getAmount(), null);
        return transactionMapper.toResponse(operation);
    }

    @Override
    public TransactionDto transferByAccountDetails(AccountDetailsTransferForm accountDetailsTransferForm) {
        BankAccount currentAccount = bankAccountRepository.findById(accountDetailsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (currentAccount.getDeposit() < accountDetailsTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        // TODO implement
        BankAccount anotherAccount = bankAccountRepository.findByContractNumber(accountDetailsTransferForm.getContractNumber());
        Operation operation = createTransaction(currentAccount, anotherAccount, accountDetailsTransferForm.getAmount(), accountDetailsTransferForm.getMessage());
        return transactionMapper.toResponse(operation);
    }

    @Override
    public TransactionDto replenishByCard(CardReplenishForm cardReplenishForm) {
        BankAccount currentAccount = bankAccountRepository.findByCardNumber(cardReplenishForm.getCard());
        if (currentAccount.getDeposit() < cardReplenishForm.getAmount()) {
            throw new RuntimeException();
        }
        BankAccount anotherAccount = bankAccountRepository.findById(cardReplenishForm.getTo()).orElseThrow(IllegalArgumentException::new);
        Operation operation = createTransaction(currentAccount, anotherAccount, cardReplenishForm.getAmount(), null);
        return transactionMapper.toResponse(operation);
    }

    private Operation createTransaction(BankAccount currentAccount, BankAccount anotherAccount, Long amount, String message) {
        Operation operation = Operation.builder()
                .date(LocalDate.now())
                .amount(amount)
                .from(currentAccount)
                .to(anotherAccount)
                .message(message)
                .build();

        operation = transactionRepository.save(operation);
        currentAccount.setDeposit(currentAccount.getDeposit() - amount);
        anotherAccount.setDeposit(anotherAccount.getDeposit() + amount);
        return operation;
    }
}
