package ru.kpfu.itis.lobanov.dtos.responses;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public final class Violation {
    private final String fieldName;
    private final String message;
}
