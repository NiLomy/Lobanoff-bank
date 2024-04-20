package ru.kpfu.itis.lobanov.api.cards;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Card Details", description = "User's card details.")
@RequestMapping("/card")
public interface CardDetailsApi {
    @Operation(summary = "Get a page of card details.", description = "Returns a card details page for a user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    String getCardDetailsPage(@Parameter(description = "Id of the current user's card.", example = "123") @PathVariable("id") String cardId, Model model);
}
