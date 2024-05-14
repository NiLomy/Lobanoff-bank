package ru.kpfu.itis.lobanov.cashbackservice.services;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.CashbackCategoryDto;

import static ru.kpfu.itis.lobanov.cashbackservice.utils.ValidationMessages.ID_NOT_NULL;
import static ru.kpfu.itis.lobanov.cashbackservice.utils.ValidationMessages.ID_POSITIVE_OR_ZERO;

@Validated
public interface CashbackCategoryService {
    CashbackCategoryDto getByCategoryId(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long categoryId
    );
}
