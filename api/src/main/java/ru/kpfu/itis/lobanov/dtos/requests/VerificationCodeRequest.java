package ru.kpfu.itis.lobanov.dtos.requests;

import lombok.Data;

@Data
public class VerificationCodeRequest {
    private String code;
}
