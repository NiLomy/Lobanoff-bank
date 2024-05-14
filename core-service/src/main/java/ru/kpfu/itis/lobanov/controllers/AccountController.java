package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.AccountApi;
import ru.kpfu.itis.lobanov.data.services.AccountService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class AccountController implements AccountApi {
    private final AccountService accountService;

    @Override
    public ResponseEntity<BankAccountDto> getAccountById(String accountId) {
        try {
            BankAccountDto bankAccount = accountService.getAccountById(Long.parseLong(accountId));

            if (bankAccount == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<BankAccountDto>> getAllAccounts() {
        List<BankAccountDto> accounts = accountService.getAllAccounts();

        if (accounts == null || accounts.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<BankAccountDto>> getAllUserAccounts(String userId) {
        try {
            List<BankAccountDto> accounts = accountService.getAllUserAccounts(Long.parseLong(userId));

            if (accounts == null || accounts.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(accounts, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<List<BankAccountDto>> getAllUserCardAccounts(String userId) {
        try {
            List<BankAccountDto> accounts = accountService.getAllUserCardAccounts(Long.parseLong(userId));

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
    public ResponseEntity<BankAccountDto> createAccount(CreateAccountRequest request) {
        try {
            BankAccountDto bankAccount = accountService.createAccount(request);

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
    public ResponseEntity<BankAccountDto> bindCardToAccount(BindCardRequest request) {
        try {
            BankAccountDto bankAccount = accountService.bindCard(request);

            if (bankAccount == null) return new ResponseEntity<>(HttpStatus.FORBIDDEN);

            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<BankAccountDto> updateAccountName(String accountId, String name) {
        try {
            BankAccountDto bankAccount = accountService.updateAccountName(Long.parseLong(accountId), name);

            if (bankAccount == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(bankAccount, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
