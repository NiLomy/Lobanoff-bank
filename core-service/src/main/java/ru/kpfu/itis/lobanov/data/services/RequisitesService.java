package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.ID_NOT_NULL;
import static ru.kpfu.itis.lobanov.utils.ValidationMessages.ID_POSITIVE_OR_ZERO;

@Validated
public interface RequisitesService {
    RequisitesDto getById(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long requisitesId
    );

    RequisitesDto getRequisites(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long accountId
    );
}
