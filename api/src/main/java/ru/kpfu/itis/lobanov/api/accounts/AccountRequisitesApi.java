package ru.kpfu.itis.lobanov.api.accounts;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Account Requisites", description = "User's account requisites.")
@RequestMapping("/requisites")
public interface AccountRequisitesApi {
    @Operation(summary = "Get a page of account requisites.", description = "Returns a page of user's current account requisites.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    String getAccountRequisitesPage(@Parameter(description = "Id of the current user's account.", example = "123") @PathVariable("id") String accountId, Model model);
}
