package ru.kpfu.itis.lobanov.dtos.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class BetweenAccountsTransferForm {
    @Schema(description = "Id of the bank account to transfer from.", example = "123")
    @NotNull(message = "Account to transfer from is required!")
    @PositiveOrZero(message = "Id should not be negative.")
    private Long from;
    @Schema(description = "Id of the bank account to transfer to.", example = "123")
    @NotNull(message = "Account to transfer to is required!")
    @PositiveOrZero(message = "Id should not be negative.")
    private Long to;
    @Schema(description = "The amount of money that needs to be transferred.", example = "10000")
    @NotNull(message = "The amount of money shouldn't be null.")
    @PositiveOrZero(message = "Amount should not be negative.")
    private BigDecimal amount;
}
