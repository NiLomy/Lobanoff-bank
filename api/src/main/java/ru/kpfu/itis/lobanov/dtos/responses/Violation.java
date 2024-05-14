package ru.kpfu.itis.lobanov.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Violation {
    @NotNull
    @NotBlank
    private final String fieldName;
    private final String message;
}
