package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.AuthenticationApi;
import ru.kpfu.itis.lobanov.data.services.AuthenticationService;
import ru.kpfu.itis.lobanov.dtos.forms.LoginForm;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.requests.RefreshTokenRequest;
import ru.kpfu.itis.lobanov.dtos.requests.VerificationCodeRequest;
import ru.kpfu.itis.lobanov.dtos.responses.TokenResponse;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final AuthenticationService authenticationService;

    @Override
    public ResponseEntity<TokenResponse> registerUser(RegistrationForm registrationForm) {
        TokenResponse token = authenticationService.register(registrationForm);
        if (token == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(token, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TokenResponse> verifyUser(VerificationCodeRequest request) {
        TokenResponse token = authenticationService.verify(request.getCode());
        if (token != null) {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @Override
    public ResponseEntity<TokenResponse> loginUser(LoginForm loginForm) {
        return new ResponseEntity<>(authenticationService.login(loginForm), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<TokenResponse> getAccessToken(RefreshTokenRequest request) {
        return new ResponseEntity<>(authenticationService.getAccessToken(request.getRefreshToken()), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<TokenResponse> refreshTokens(RefreshTokenRequest request) {
        return new ResponseEntity<>(authenticationService.refreshTokens(request.getRefreshToken()), HttpStatus.CREATED);
    }
}
