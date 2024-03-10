package ru.kpfu.itis.lobanov.api.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Account Details", description = "User's account details.")
@RequestMapping("/account")
public interface AccountDetailsApi {
    @Operation(summary = "Get a page of account details.", description = "Returns a page of user's current account.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Page not found.")
    })
    @GetMapping("/{id}")
    String showAccountDetailsPage(@Parameter(description = "Id of the current user's account.", example = "123") @PathVariable("id") String accountId, @Parameter(description = "Model of the page.") Model model, @Parameter(description = "Authentication of the current user.") Authentication authentication);

    @Operation(summary = "Update user's current account name.", description = "Returns a page of user's current account with updated name.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "404", description = "Page not found.")
    })
    @PostMapping("/{id}")
    String updateAccountName(@Parameter(description = "Id of the current user's account.", example = "123") @PathVariable("id") String accountId, @Parameter(description = "New name of the user's current account.", example = "My new account") @RequestParam("name") String name, @Parameter(description = "Model of the page.") Model model);
}
