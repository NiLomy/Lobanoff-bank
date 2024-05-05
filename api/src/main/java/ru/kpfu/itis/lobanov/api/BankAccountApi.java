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

@Tag(name = "Bank Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/accounts", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface BankAccountApi {
    @Operation(summary = "Get account by id.", description = "Returns user's account by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<BankAccountDto> getAccountById(
            @Parameter(description = "Id of the current user's account.", example = "123", required = true)
            @PathVariable("id")
            String accountId
    );

    @GetMapping("/all")
    ResponseEntity<List<BankAccountDto>> getAllAccounts();

    @GetMapping("/all/user/{id}")
    ResponseEntity<List<BankAccountDto>> getAllUserAccounts(@PathVariable("id") String userId);

    @GetMapping("/all-cards/user/{id}")
    ResponseEntity<List<BankAccountDto>> getAllUserCardAccounts(@PathVariable("id") String userId);

    @GetMapping("/statement/{id}")
    ResponseEntity<AccountStatementDto> getAccountStatement(@PathVariable("id") String accountId, @RequestParam("date") String date);

    @PostMapping("/create")
    ResponseEntity<BankAccountDto> createAccount(@RequestBody CreateAccountRequest request);

    @PostMapping("/close")
    ResponseEntity<?> closeAccount(@RequestBody CloseAccountRequest request);

    @PostMapping("/bind-card")
    ResponseEntity<BankAccountDto> bindCardToAccount(@RequestBody BindCardRequest request);

    @PatchMapping("/update/name/{id}")
    ResponseEntity<BankAccountDto> updateAccountName(@PathVariable("id") String accountId, @RequestParam("name") String name);
}
