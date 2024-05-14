package ru.kpfu.itis.lobanov.dtos.responses;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class ValidationErrorResponse {
    @NotNull
    private final List<Violation> violations;
}
