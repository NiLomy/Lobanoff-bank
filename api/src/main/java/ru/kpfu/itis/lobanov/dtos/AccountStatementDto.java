package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class AccountStatementDto {
    @Schema(description = "Balance at the beginning of the period.", example = "1000")
    @NotNull(message = "Balance shouldn't be null.")
    @PositiveOrZero
    private BigDecimal startBalance;
    @Schema(description = "The amount of receipts.", example = "1000")
    @NotNull(message = "Receipts shouldn't be null.")
    @PositiveOrZero
    private BigDecimal receipts;
    @Schema(description = "The amount of accrued interest.", example = "1000")
    @NotNull(message = "The amount of accrued interest shouldn't be null.")
    @PositiveOrZero
    private BigDecimal interest;
    private BigDecimal cashback;
    @Schema(description = "The amount of expenses.", example = "1000")
    @NotNull(message = "Expenses shouldn't be null.")
    @PositiveOrZero
    private BigDecimal expenses;
    @Schema(description = "Balance at the end of the period.", example = "1000")
    @NotNull(message = "Balance shouldn't be null.")
    @PositiveOrZero
    private BigDecimal endBalance;
}
