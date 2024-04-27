package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.util.List;

@Tag(name = "Bank Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/transactions", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface TransactionApi {
    @GetMapping("/{id}")
    ResponseEntity<TransactionDto> getTransactionById(@PathVariable("id") String transactionId);

    @GetMapping("/account/{id}")
    ResponseEntity<List<TransactionDto>> getRecentTransactionsFromAccount(@PathVariable("id") String accountId);

    @PostMapping("/transfer/phone")
    ResponseEntity<TransactionDto> transferByPhone(@RequestBody PhoneTransferForm phoneTransferForm);

    @PostMapping("/transfer/between-accounts")
    ResponseEntity<TransactionDto> transferBetweenAccounts(@RequestBody BetweenAccountsTransferForm betweenAccountsTransferForm);

    @PostMapping("/transfer/card")
    ResponseEntity<TransactionDto> transferByCard(@RequestBody CardTransferForm cardTransferForm);

    @PostMapping("/transfer/account-details")
    ResponseEntity<TransactionDto> transferByAccountDetails(@RequestBody AccountDetailsTransferForm accountDetailsTransferForm);

    @PostMapping("/replenish/card")
    ResponseEntity<TransactionDto> replenishByCard(@RequestBody CardReplenishForm cardReplenishForm);
}
