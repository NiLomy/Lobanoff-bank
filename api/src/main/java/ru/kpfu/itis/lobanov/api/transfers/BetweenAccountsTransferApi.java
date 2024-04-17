package ru.kpfu.itis.lobanov.api.transfers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.BetweenAccountsTransferForm;

@Tag(name = "Between Accounts Transfer", description = "Transfer of money between user's accounts.")
@RequestMapping("/transfers")
public interface BetweenAccountsTransferApi {
    @Operation(summary = "Get a page of a transfer between accounts.", description = "Returns a page of a transfer between accounts.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/between-accounts")
    String getTransfersPage(@Parameter(description = "Id of the current user's account.", example = "123") @RequestParam("accountId") String accountId, Model model);

    @Operation(summary = "Transfer money between accounts.", description = "Returns a page of user's current account with transferred money.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/between-accounts")
    String transferBetweenAccounts(@Parameter(description = "Info about accounts to make a transfer.") @Valid @RequestBody BetweenAccountsTransferForm betweenAccountsTransferForm, Model model);
}
