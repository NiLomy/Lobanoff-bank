package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.PassportDto;
import ru.kpfu.itis.lobanov.dtos.requests.SavePassportRequest;
import ru.kpfu.itis.lobanov.dtos.requests.UpdatePassportRequest;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface PassportService {
    PassportDto getById(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long id
    );

    PassportDto save(
            @NotNull(message = PASSPORT_NOT_NULL)
            SavePassportRequest request
    );

    PassportDto update(
            @NotNull(message = PASSPORT_NOT_NULL)
            UpdatePassportRequest request
    );
}
