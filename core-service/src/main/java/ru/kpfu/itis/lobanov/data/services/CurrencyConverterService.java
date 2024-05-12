package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;

import java.math.BigDecimal;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface CurrencyConverterService {
    BigDecimal convert(
            @NotNull(message = CURRENCY_CODE_NOT_NULL)
            @NotBlank(message = CURRENCY_CODE_NOT_BLANK)
            String currencyFrom,
            @NotNull(message = CURRENCY_CODE_NOT_NULL)
            @NotBlank(message = CURRENCY_CODE_NOT_BLANK)
            String currencyTo,
            @NotNull(message = AMOUNT_NOT_NULL)
            @PositiveOrZero(message = AMOUNT_POSITIVE_OR_ZERO)
            BigDecimal amount
    );
}
