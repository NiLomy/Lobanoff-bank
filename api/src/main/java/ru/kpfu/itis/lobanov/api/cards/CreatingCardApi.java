package ru.kpfu.itis.lobanov.api.cards;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@Tag(name = "Creating Card", description = "Creating credit card for a user.")
@RequestMapping("/adding-card")
public interface CreatingCardApi {
    @Operation(summary = "Get a page of card creation.", description = "Returns a card creating page for a user.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    String getCreatingCardPage(@Parameter(description = "Id of the current user's account.", example = "123") @PathVariable("id") String accountId, Model model);

    @Operation(summary = "Create a card.", description = "Returns a page of user's current account with added card.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/{id}")
    String createCard(@Parameter(description = "Id of the current user's account.", example = "123") @PathVariable("id") String accountId, Model model);
}
