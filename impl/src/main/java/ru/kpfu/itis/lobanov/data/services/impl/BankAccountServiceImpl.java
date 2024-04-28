package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.entities.Operation;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.BankInfoRepository;
import ru.kpfu.itis.lobanov.data.repositories.CardRepository;
import ru.kpfu.itis.lobanov.data.repositories.TransactionRepository;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.DateService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;

import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final CardRepository cardRepository;
    private final Mapper<BankAccount, BankAccountDto> bankAccountMapper;
    private final TransactionRepository transactionRepository;
    private final DateService dateService;

    @Override
    public List<BankAccountDto> getAllAccounts() {
        return bankAccountMapper.toListResponse(bankAccountRepository.findAll());
    }

    @Override
    public List<BankAccountDto> getAllUserAccounts(Long userId) {
        return bankAccountMapper.toListResponse(bankAccountRepository.findAllByOwnerId(userId));
    }

    @Override
    public List<BankAccountDto> getAllUserCardAccounts(Long userId) {
        return bankAccountMapper.toListResponse(bankAccountRepository.findAllCardsByOwnerId(userId));
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
    public BankAccountDto bindCard(BindCardRequest request) {
        BankAccount account = bankAccountRepository.findById(Long.parseLong(request.getAccountId())).orElseThrow(IllegalArgumentException::new);
        Card card = cardRepository.findById(Long.parseLong(request.getCardId())).orElseThrow(IllegalArgumentException::new);
        account.getCards().add(card);
        return bankAccountMapper.toResponse(bankAccountRepository.save(account));
    }

    @Override
    public AccountStatementDto getStatement(Long accountId, String date) {
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(IllegalArgumentException::new);

        LocalDate dateFrom = dateService.getFullDate(date);
        LocalDate dateTo = dateService.getNextMonth(date);

        Long lastMonthDeposit = transactionRepository.getBeginningMonthDeposit(account, dateFrom);
        if (lastMonthDeposit == null) lastMonthDeposit = 0L;

        List<Operation> replenishmentOperations = transactionRepository.findAllByAccountToAndBetweenDates(account, dateFrom, dateTo);
        List<Operation> transferOperations = transactionRepository.findAllByAccountFromAndBetweenDates(account, dateFrom, dateTo);

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
}
