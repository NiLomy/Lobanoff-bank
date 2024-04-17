package ru.kpfu.itis.lobanov.api.transfers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CardReplenishForm;
import ru.kpfu.itis.lobanov.dtos.CardTransferForm;

@Tag(name = "Card Replenish", description = "Replenish of money by card info.")
@RequestMapping("/replenishes")
public interface CardReplenishApi {
    @Operation(summary = "Get a page of a card replenish.", description = "Returns a page of a card replenish.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/card")
    String getReplenishPage(@Parameter(description = "Id of the current user's account.", example = "123") @RequestParam("accountId") String accountId, Model model);

    @Operation(summary = "Replenish money by credit card number.", description = "Returns a page of user's current account with replenished money.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/card")
    String replenishByCreditCardNumber(@Parameter(description = "Info about credit card to make a replenish.") @Valid @RequestBody CardReplenishForm cardReplenishForm, Model model);
}
