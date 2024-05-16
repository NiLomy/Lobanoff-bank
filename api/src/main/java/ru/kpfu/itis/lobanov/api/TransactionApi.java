package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.util.List;

@Tag(name = "Transaction Api", description = "Provides methods for transactions")
@RequestMapping(path = "/api/v1/transactions", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface TransactionApi {
    @Operation(summary = "Get transaction by id.", description = "Returns transaction by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transaction not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<TransactionDto> getTransactionById(
            @Parameter(description = "Id of the transaction.", example = "123", required = true)
            @PathVariable("id")
            String transactionId
    );

    @Operation(summary = "Get recent transactions by account id.", description = "Returns recent transactions by provided account id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transactions not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/account/{id}")
    ResponseEntity<List<TransactionDto>> getRecentTransactionsFromAccount(
            @Parameter(description = "Id of the user's account.", example = "123", required = true)
            @PathVariable("id")
            String accountId
    );

    @Operation(summary = "Get recent transactions by user id.", description = "Returns recent transactions by provided user id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transactions not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("expenses/user/{id}")
    ResponseEntity<List<TransactionDto>> getAllTransactionsFromUserExpenses(
            @Parameter(description = "Id of the user's account.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );

    @Operation(summary = "Get recent transactions by user id.", description = "Returns recent transactions by provided user id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transactions not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("receipts/user/{id}")
    ResponseEntity<List<TransactionDto>> getAllTransactionsFromUserReceipts(
            @Parameter(description = "Id of the user's account.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );

    @Operation(summary = "Transfer by phone.", description = "Returns transaction which was transferred by phone.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transaction not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/transfer/phone")
    ResponseEntity<TransactionDto> transferByPhone(
            @Parameter(description = "Form of phone transfer.", required = true)
            @RequestBody
            PhoneTransferForm phoneTransferForm
    );

    @Operation(summary = "Transfer between accounts.", description = "Returns transaction which was transferred between accounts.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transaction not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/transfer/between-accounts")
    ResponseEntity<TransactionDto> transferBetweenAccounts(
            @Parameter(description = "Form of between accounts transfer.", required = true)
            @RequestBody
            BetweenAccountsTransferForm betweenAccountsTransferForm
    );

    @Operation(summary = "Transfer by card.", description = "Returns transaction which was transferred by card.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Transaction not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/transfer/card")
    ResponseEntity<TransactionDto> transferByCard(
            @Parameter(description = "Form of card transfer.", required = true)
            @RequestBody
            CardTransferForm cardTransferForm
    );

    @PostMapping("/transfer/account-details")
    ResponseEntity<TransactionDto> transferByAccountDetails(@RequestBody AccountDetailsTransferForm accountDetailsTransferForm);

    @PostMapping("/replenish/card")
    ResponseEntity<TransactionDto> replenishByCard(@RequestBody CardReplenishForm cardReplenishForm);
}
