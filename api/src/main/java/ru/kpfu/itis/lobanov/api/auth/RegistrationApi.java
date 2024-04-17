package ru.kpfu.itis.lobanov.api.auth;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.RegistrationForm;

@Tag(name = "Registration", description = "Registration form.")
@RequestMapping("/register")
public interface RegistrationApi {
    @Operation(summary = "Get a page of registration.", description = "Returns a page of registration form.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping
    String showRegistrationPage(Model model);

    @Operation(summary = "Register a new user.", description = "Returns a login page and register a new bank client.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Page not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping
    String register(@Parameter(description = "Registration data of the user.") @Valid @ModelAttribute("registrationForm") @RequestBody RegistrationForm registrationForm, Errors errors);
}
