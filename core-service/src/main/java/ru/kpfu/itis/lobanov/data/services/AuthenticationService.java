package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.forms.LoginForm;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.responses.TokenResponse;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface AuthenticationService {
    TokenResponse register(
            @NotNull(message = REGISTRATION_FORM_NOT_NULL)
            RegistrationForm registrationForm
    );

    TokenResponse verify(
            @NotNull(message = SECRET_CODE_NOT_NULL)
            @NotBlank(message = SECRET_CODE_NOT_BLANK)
            String code
    );

    TokenResponse login(
            @NotNull(message = LOGIN_FORM_NOT_NULL)
            LoginForm loginForm
    );

    TokenResponse getAccessToken(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String refreshToken
    );

    TokenResponse refreshTokens(
            @NotNull(message = TOKEN_NOT_NULL)
            @NotBlank(message = TOKEN_NOT_BLANK)
            String refreshToken
    );
}
