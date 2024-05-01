package ru.kpfu.itis.lobanov.data.services;

import org.springframework.scheduling.annotation.Scheduled;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> getAllAccounts();

    List<BankAccountDto> getAllUserAccounts(Long userId);

    List<BankAccountDto> getAllUserCardAccounts(Long userId);

    void createAccount(BankAccountDto bankAccountDto);

    void closeAccount(BankAccountDto bankAccountDto);

    BankAccountDto getAccountById(Long id);

    BankAccountDto getUserMainAccount(Long userId);

    BankAccountDto updateAccountName(Long id, String name);

    BankAccountDto bindCard(BindCardRequest request);

    AccountStatementDto getStatement(Long accountId, String date);
}
