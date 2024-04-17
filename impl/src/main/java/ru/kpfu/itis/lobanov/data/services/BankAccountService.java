package ru.kpfu.itis.lobanov.data.services;

import org.springframework.scheduling.annotation.Scheduled;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> getAllAccounts();

    List<BankAccountDto> getAllUserAccounts(UserDto userDto);

    List<BankAccountDto> getAllUserCardAccounts(UserDto userDto);

    void createAccount(BankAccountDto bankAccountDto);

    void closeAccount(BankAccountDto bankAccountDto);

    BankAccountDto getAccountById(Long id);

    BankAccountDto updateAccountName(Long id, String name);

    BankAccountDto bindCard(Long accountId, CardDto cardDto);

    AccountStatementDto getStatement(Long accountId);

    void updateBeginningMonthDeposit();
}
