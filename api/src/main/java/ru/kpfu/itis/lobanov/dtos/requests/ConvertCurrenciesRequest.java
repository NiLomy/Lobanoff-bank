package ru.kpfu.itis.lobanov.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ConvertCurrenciesRequest {
    @NotNull
    @NotBlank
    private String currencyFrom;
    @NotNull
    @NotBlank
    private String currencyTo;
    @NotNull
    @PositiveOrZero
    private BigDecimal amount;
}
