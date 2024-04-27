package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.TransactionApi;
import ru.kpfu.itis.lobanov.data.services.TransactionService;
import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class TransactionController implements TransactionApi {
    private final TransactionService transactionService;

    @Override
    public ResponseEntity<TransactionDto> getTransactionById(String transactionId) {
        TransactionDto transaction = transactionService.getById(Long.parseLong(transactionId));

        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<TransactionDto>> getRecentTransactionsFromAccount(String accountId) {
        List<TransactionDto> transactions = transactionService.getAllRecentTransactions(Long.parseLong(accountId));

        if (transactions == null || transactions.isEmpty()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transactions, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> transferByPhone(PhoneTransferForm phoneTransferForm) {
        TransactionDto transaction = transactionService.transferByPhone(phoneTransferForm);

        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm) {
        TransactionDto transaction = transactionService.transferBetweenAccounts(betweenAccountsTransferForm);

        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> transferByCard(CardTransferForm cardTransferForm) {
        TransactionDto transaction = transactionService.transferByCard(cardTransferForm);

        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> transferByAccountDetails(AccountDetailsTransferForm accountDetailsTransferForm) {
        TransactionDto transaction = transactionService.transferByAccountDetails(accountDetailsTransferForm);

        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TransactionDto> replenishByCard(CardReplenishForm cardReplenishForm) {
        TransactionDto transaction = transactionService.replenishByCard(cardReplenishForm);

        if (transaction == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(transaction, HttpStatus.OK);
    }
}
