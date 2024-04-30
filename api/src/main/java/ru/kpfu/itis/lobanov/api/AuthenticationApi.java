package ru.kpfu.itis.lobanov.api;

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
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:8080"})
public interface AuthenticationApi {
    @PostMapping("/register")
    ResponseEntity<TokenResponse> registerUser(@RequestBody RegistrationForm registrationForm);

    @PostMapping("/verify")
    ResponseEntity<TokenResponse> verifyUser(@RequestBody VerificationCodeRequest request);

    @PostMapping("/login")
    ResponseEntity<TokenResponse> loginUser(@RequestBody LoginForm loginForm);

    @PostMapping("/access")
    ResponseEntity<TokenResponse> getAccessToken(@RequestBody RefreshTokenRequest request);

    @PostMapping("/refresh")
    ResponseEntity<TokenResponse> refreshTokens(@RequestBody RefreshTokenRequest request);
}
