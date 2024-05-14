package ru.kpfu.itis.lobanov.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class TokenResponse {
    @NotNull
    @NotBlank
    private String accessToken;
    @NotNull
    @NotBlank
    private String refreshToken;
}
