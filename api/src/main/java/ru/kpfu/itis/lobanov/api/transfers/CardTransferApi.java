package ru.kpfu.itis.lobanov.api.transfers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CardTransferForm;

@Tag(name = "Account Details Transfer", description = "Transfer of money by account details.")
@RequestMapping("/transfers")
public interface CardTransferApi {
    @Operation(summary = "Get a page of a card transfer.", description = "Returns a page of a credit card transfer.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/card")
    String getTransfersPage(@Parameter(description = "Id of the current user's account.", example = "123") @RequestParam("accountId") String accountId, Model model);

    @Operation(summary = "Transfer money by credit card number.", description = "Returns a page of user's current account with transferred money.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/card")
    String transferByCreditCardNumber(@Parameter(description = "Info of the credit card to make a transfer.") @Valid @RequestBody CardTransferForm cardTransferForm, Model model);
}
