package ru.kpfu.itis.lobanov.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class RefreshTokenRequest {
    @NotNull
    @NotBlank
    private String refreshToken;
}
