package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.Data;

@Data
public class RefreshTokenRequest {
    private String refreshToken;
}
