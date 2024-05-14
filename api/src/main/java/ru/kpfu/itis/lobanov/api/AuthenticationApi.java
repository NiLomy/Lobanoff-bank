package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.forms.LoginForm;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.requests.RefreshTokenRequest;
import ru.kpfu.itis.lobanov.dtos.requests.VerificationCodeRequest;
import ru.kpfu.itis.lobanov.dtos.responses.TokenResponse;

@Tag(name = "Authentication Api", description = "Provides methods for user authentication")
@RequestMapping(path = "/api/v1/auth", produces = "application/json")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080", "http://localhost:8090"})
public interface AuthenticationApi {
    @Operation(summary = "Register user.", description = "Returns tokens after user registration.", responses = {
            @ApiResponse(responseCode = "201", description = "User was successfully registered."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/register")
    ResponseEntity<TokenResponse> registerUser(
            @Parameter(description = "Form of user registration.", required = true)
            @RequestBody
            RegistrationForm registrationForm
    );

    @Operation(summary = "Verify user.", description = "Returns tokens after user verification with code.", responses = {
            @ApiResponse(responseCode = "200", description = "User was successfully verified."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/verify")
    ResponseEntity<TokenResponse> verifyUser(
            @Parameter(description = "Request for user verification.", required = true)
            @RequestBody
            VerificationCodeRequest request
    );

    @Operation(summary = "Login user.", description = "Returns tokens after user login.", responses = {
            @ApiResponse(responseCode = "200", description = "User was successfully logged in."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/login")
    ResponseEntity<TokenResponse> loginUser(
            @Parameter(description = "Form of user login.", required = true)
            @RequestBody
            LoginForm loginForm
    );

    @Operation(summary = "Get access token.", description = "Returns access token by refresh token.", responses = {
            @ApiResponse(responseCode = "201", description = "Access token was successfully created."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/access")
    ResponseEntity<TokenResponse> getAccessToken(
            @Parameter(description = "Request for a new access token.", required = true)
            @RequestBody
            RefreshTokenRequest request
    );

    @Operation(summary = "Get refresh token.", description = "Returns tokens by refresh token.", responses = {
            @ApiResponse(responseCode = "201", description = "Tokens were successfully created."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refreshTokens(
            @Parameter(description = "Request for a new refresh and access tokens.", required = true)
            @RequestBody
            RefreshTokenRequest request
    );
}
