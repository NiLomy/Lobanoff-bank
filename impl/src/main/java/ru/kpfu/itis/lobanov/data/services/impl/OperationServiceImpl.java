package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.OperationMapper;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.OperationRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.*;

import java.util.Date;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OperationServiceImpl implements OperationService {
    private final OperationRepository operationRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final OperationMapper operationMapper;

    @Override
    public List<OperationDto> getAllOperationsFromUser(UserDto userDto) {
        return operationMapper.toListResponse(operationRepository.findAllByUser(userDto.getId()));
    }

    @Override
    public List<OperationDto> findAllByUserLimitRecent(BankAccountDto bankAccountDto) {
        return operationMapper.toListResponse(operationRepository.findAllByUserLimitRecent(bankAccountDto.getId()));
    }

    @Override
    public OperationDto transferByPhone(Long accountId, PhoneTransferForm phoneTransferForm) {
        BankAccount currentAccount = bankAccountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);
        if (currentAccount.getDeposit() < phoneTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        User anotherUser = userRepository.findByPhone(phoneTransferForm.getPhone());
        BankAccount anotherAccount = bankAccountRepository.findAllByOwnerId(anotherUser.getId()).get(0);
        Operation operation = createTransaction(currentAccount, anotherAccount, phoneTransferForm.getAmount(), phoneTransferForm.getMessage());
        return operationMapper.toResponse(operation);
    }

    @Override
    public OperationDto transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm) {
        BankAccount accountToTransferFrom = bankAccountRepository.findById(betweenAccountsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (accountToTransferFrom.getDeposit() < betweenAccountsTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        BankAccount accountToTransferTo = bankAccountRepository.findById(betweenAccountsTransferForm.getTo()).orElseThrow(IllegalArgumentException::new);
        Operation operation = createTransaction(accountToTransferFrom, accountToTransferTo, betweenAccountsTransferForm.getAmount(), null);
        return operationMapper.toResponse(operation);
    }

    @Override
    public OperationDto transferByCard(CardTransferForm cardTransferForm) {
        BankAccount currentAccount = bankAccountRepository.findById(cardTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (currentAccount.getDeposit() < cardTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        BankAccount anotherAccount = bankAccountRepository.findByCardNumber(cardTransferForm.getCard());
        Operation operation = createTransaction(currentAccount, anotherAccount, cardTransferForm.getAmount(), null);
        return operationMapper.toResponse(operation);
    }

    @Override
    public OperationDto transferByAccountDetails(AccountDetailsTransferForm accountDetailsTransferForm) {
        BankAccount currentAccount = bankAccountRepository.findById(accountDetailsTransferForm.getFrom()).orElseThrow(IllegalArgumentException::new);
        if (currentAccount.getDeposit() < accountDetailsTransferForm.getAmount()) {
            throw new RuntimeException();
        }
        // TODO implement
        BankAccount anotherAccount = bankAccountRepository.findByContractNumber(accountDetailsTransferForm.getContractNumber());
        Operation operation = createTransaction(currentAccount, anotherAccount, accountDetailsTransferForm.getAmount(), accountDetailsTransferForm.getMessage());
        return operationMapper.toResponse(operation);
    }

    @Override
    public OperationDto replenishByCard(CardReplenishForm cardReplenishForm) {
        BankAccount currentAccount = bankAccountRepository.findByCardNumber(cardReplenishForm.getCard());
        if (currentAccount.getDeposit() < cardReplenishForm.getAmount()) {
            throw new RuntimeException();
        }
        BankAccount anotherAccount = bankAccountRepository.findById(cardReplenishForm.getTo()).orElseThrow(IllegalArgumentException::new);
        Operation operation = createTransaction(currentAccount, anotherAccount, cardReplenishForm.getAmount(), null);
        return operationMapper.toResponse(operation);
    }

    private Operation createTransaction(BankAccount currentAccount, BankAccount anotherAccount, Long amount, String message) {
        Operation operation = Operation.builder()
                .date(new Date())
                .amount(amount)
                .from(currentAccount)
                .to(anotherAccount)
                .message(message)
                .build();

        operation = operationRepository.save(operation);
        currentAccount.setDeposit(currentAccount.getDeposit() - amount);
        anotherAccount.setDeposit(anotherAccount.getDeposit() + amount);
        return operation;
    }
}
