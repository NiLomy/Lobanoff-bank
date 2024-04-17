package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.BankAccountMapper;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.CardRepository;
import ru.kpfu.itis.lobanov.data.repositories.OperationRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final CardRepository cardRepository;
    private final BankAccountMapper bankAccountMapper;
    private final OperationRepository operationRepository;

    @Override
    public List<BankAccountDto> getAllAccounts() {
        return bankAccountMapper.toListResponse(bankAccountRepository.findAll());
    }

    @Override
    public List<BankAccountDto> getAllUserAccounts(UserDto userDto) {
        return bankAccountMapper.toListResponse(bankAccountRepository.findAllByOwnerId(userDto.getId()));
    }

    @Override
    public List<BankAccountDto> getAllUserCardAccounts(UserDto userDto) {
        return bankAccountMapper.toListResponse(bankAccountRepository.findAllCardsByOwnerId(userDto.getId()));
    }

    @Override
    public void createAccount(BankAccountDto bankAccountDto) {

    }

    @Override
    public void closeAccount(BankAccountDto bankAccountDto) {

    }

    @Override
    public BankAccountDto getAccountById(Long id) {
        BankAccount account = bankAccountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return bankAccountMapper.toResponse(account);
    }

    @Override
    public BankAccountDto updateAccountName(Long id, String name) {
        bankAccountRepository.updateNameById(id, name);
        BankAccount account = bankAccountRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        return bankAccountMapper.toResponse(account);
    }

    @Override
    public BankAccountDto bindCard(Long accountId, CardDto cardDto) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);
        Card card = cardRepository.findById(cardDto.getId()).orElseThrow(IllegalArgumentException::new);
        account.getCards().add(card);
        return bankAccountMapper.toResponse(bankAccountRepository.save(account));
    }

    @Override
    public AccountStatementDto getStatement(Long accountId) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);
        Long lastMonthDeposit = account.getBeginningMonthDeposit();
        LocalDate localDate = LocalDate.now();
        localDate = localDate.minusMonths(1);
        List<Operation> replenishmentOperations = operationRepository.findAllByToAndDateAfter(account, localDate);
        List<Operation> transferOperations = operationRepository.findAllByFromAndDateAfter(account, localDate);
        long replenishment = replenishmentOperations.stream().map(Operation::getAmount).mapToLong(Long::longValue).sum();
        long transfer = transferOperations.stream().map(Operation::getAmount).mapToLong(Long::longValue).sum();
        Long newDeposit = lastMonthDeposit + replenishment - transfer;

        return AccountStatementDto.builder()
                .startBalance(lastMonthDeposit)
                .receipts(replenishment)
                .interest(0L)
                .expenses(transfer)
                .endBalance(newDeposit)
                .build();
    }

    @Override
//    @Scheduled(cron = "*/10 * * * * *")
    public void updateBeginningMonthDeposit() {
        List<BankAccount> accounts = bankAccountRepository.findAll();
        for (BankAccount account : accounts) {
            Long lastMonthDeposit = account.getBeginningMonthDeposit();
            if (lastMonthDeposit == null) lastMonthDeposit = 0L;

            LocalDate localDate = LocalDate.now();
            localDate = localDate.minusMonths(1);
            List<Operation> replenishmentOperations = operationRepository.findAllByToAndDateAfter(account, localDate);
            List<Operation> transferOperations = operationRepository.findAllByFromAndDateAfter(account, localDate);
            long replenishment = replenishmentOperations.stream().map(Operation::getAmount).mapToLong(Long::longValue).sum();
            long transfer = transferOperations.stream().map(Operation::getAmount).mapToLong(Long::longValue).sum();
            Long newDeposit = lastMonthDeposit + replenishment - transfer;
            bankAccountRepository.updateBeginningMonthDepositById(account.getId(), newDeposit);
        }
    }
}
