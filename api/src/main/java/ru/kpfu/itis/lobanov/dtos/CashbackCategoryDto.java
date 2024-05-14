package ru.kpfu.itis.lobanov.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CashbackCategoryDto {
    @NotNull
    @PositiveOrZero
    private Long categoryId;
    @NotNull
    @PositiveOrZero
    private BigDecimal cashbackPercentage;
}
