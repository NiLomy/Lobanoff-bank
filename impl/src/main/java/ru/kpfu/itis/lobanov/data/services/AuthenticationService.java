package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.forms.LoginForm;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.responses.TokenResponse;

public interface AuthenticationService {
    TokenResponse register(@NonNull RegistrationForm registrationForm);

    void sendVerificationCode(@NonNull String mail, @NonNull String name, @NonNull String code);

    TokenResponse verify(@NonNull String code);

    TokenResponse login(@NonNull LoginForm loginForm);

    TokenResponse getAccessToken(@NonNull String refreshToken);

    TokenResponse refreshTokens(@NonNull String refreshToken);

    UserDto getCurrentUser();
}