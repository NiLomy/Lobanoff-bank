package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.requests.CreateCardRequest;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface CardService {
    CardDto getById(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long cardId
    );

    CardDto create(
            @NotNull(message = CREATE_CARD_REQUEST_NOT_NULL)
            CreateCardRequest request
    );
}
