package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.AccountApi;
import ru.kpfu.itis.lobanov.data.services.AccountService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.AccountTypeDto;
import ru.kpfu.itis.lobanov.dtos.AccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @Override
    public ResponseEntity<AccountDto> getAccountById(String accountId) {
        try {
            AccountDto bankAccount = accountService.getAccountById(Long.parseLong(accountId));

            if (bankAccount == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();

        if (accounts == null || accounts.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAllUserAccounts(String userId) {
        try {
            List<AccountDto> accounts = accountService.getAllUserAccounts(Long.parseLong(userId));

            if (accounts == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<AccountDto>> getAllUserCardAccounts(String userId) {
        try {
            List<AccountDto> accounts = accountService.getAllUserCardAccounts(Long.parseLong(userId));

            if (accounts == null || accounts.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<AccountStatementDto> getAccountStatement(String accountId, String date) {
        try {
            AccountStatementDto statement = accountService.getStatement(Long.parseLong(accountId), date);

            if (statement == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(statement, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<AccountTypeDto>> getAllTypes() {
        return new ResponseEntity<>(accountService.getAllTypes(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<AccountDto> createAccount(CreateAccountRequest request) {
        try {
            AccountDto bankAccount = accountService.createAccount(request);

            return new ResponseEntity<>(bankAccount, HttpStatus.CREATED);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<?> closeAccount(CloseAccountRequest request) {
        try {
            accountService.closeAccount(request);

            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<AccountDto> bindCardToAccount(BindCardRequest request) {
        try {
            AccountDto bankAccount = accountService.bindCard(request);

            if (bankAccount == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<AccountDto> updateAccountName(String accountId, String name) {
        try {
            AccountDto bankAccount = accountService.updateAccountName(Long.parseLong(accountId), name);

            if (bankAccount == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
