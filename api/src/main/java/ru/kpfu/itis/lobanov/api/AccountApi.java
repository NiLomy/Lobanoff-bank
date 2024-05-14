package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;

import java.util.List;

@Tag(name = "Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/accounts", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface AccountApi {
    @Operation(summary = "Get account by id.", description = "Returns user's account by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Account not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<BankAccountDto> getAccountById(
            @Parameter(description = "Id of the current user's account.", example = "123", required = true)
            @PathVariable("id")
            String accountId
    );

    @Operation(summary = "Get all accounts.", description = "Returns all accounts.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Accounts not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/all")
    ResponseEntity<List<BankAccountDto>> getAllAccounts();

    @Operation(summary = "Get all user accounts.", description = "Returns all user's accounts by user id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Accounts not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/all/user/{id}")
    ResponseEntity<List<BankAccountDto>> getAllUserAccounts(
            @Parameter(description = "Id of the user.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );

    @Operation(summary = "Get all user account with cards.", description = "Returns all user's account with cards by user id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Accounts not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/all-cards/user/{id}")
    ResponseEntity<List<BankAccountDto>> getAllUserCardAccounts(
            @Parameter(description = "Id of the user.", example = "123", required = true)
            @PathVariable("id")
            String userId
    );

    @Operation(summary = "Get statement by account id.", description = "Returns statement by provided account id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Statement not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/statement/{id}")
    ResponseEntity<AccountStatementDto> getAccountStatement(
            @Parameter(description = "Id of the current user's account.", example = "123", required = true)
            @PathVariable("id")
            String accountId,
            @Parameter(description = "Date for statement.", example = "01/24", required = true)
            @RequestParam("date")
            String date
    );

    @Operation(summary = "Create new account.", description = "Returns created bank account.", responses = {
            @ApiResponse(responseCode = "201", description = "Account was created."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/create")
    ResponseEntity<BankAccountDto> createAccount(
            @Parameter(description = "Request for account creation.", required = true)
            @RequestBody
            CreateAccountRequest request
    );

    @Operation(summary = "Close account.", description = "Closes user's account.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Account not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/close")
    ResponseEntity<?> closeAccount(
            @Parameter(description = "Request for account closure.", required = true)
            @RequestBody
            CloseAccountRequest request
    );

    @Operation(summary = "Bind card to account.", description = "Returns user's account with bound card.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "403", description = "Operation was forbidden."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/bind-card")
    ResponseEntity<BankAccountDto> bindCardToAccount(
            @Parameter(description = "Request for card binding to account.", required = true)
            @RequestBody
            BindCardRequest request
    );

    @Operation(summary = "Update account name.", description = "Returns user's account with updated name.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Account not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PatchMapping("/update/name/{id}")
    ResponseEntity<BankAccountDto> updateAccountName(
            @Parameter(description = "Id of the current user's account.", example = "123", required = true)
            @PathVariable("id")
            String accountId,
            @Parameter(description = "New name of account.", example = "Main Account", required = true)
            @RequestParam("name")
            String name
    );
}
