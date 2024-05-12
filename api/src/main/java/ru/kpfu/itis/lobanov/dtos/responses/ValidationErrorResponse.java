package ru.kpfu.itis.lobanov.dtos.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public final class ValidationErrorResponse {
    private final List<Violation> violations;
}
