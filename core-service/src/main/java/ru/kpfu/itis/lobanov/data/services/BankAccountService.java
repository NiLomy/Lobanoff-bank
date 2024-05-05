package ru.kpfu.itis.lobanov.data.services;

import org.springframework.scheduling.annotation.Scheduled;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;

import java.util.List;

public interface BankAccountService {
    List<BankAccountDto> getAllAccounts();

    List<BankAccountDto> getAllUserAccounts(Long userId);

    List<BankAccountDto> getAllUserCardAccounts(Long userId);

    BankAccountDto createAccount(CreateAccountRequest request);

    void closeAccount(CloseAccountRequest request);

    BankAccountDto getAccountById(Long id);

    BankAccountDto getUserMainAccount(Long userId);

    BankAccountDto updateAccountName(Long id, String name);

    BankAccountDto bindCard(BindCardRequest request);

    AccountStatementDto getStatement(Long accountId, String date);
}
