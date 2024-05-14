package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {
    @Schema(description = "Id of the transaction.", example = "123")
    private Long id;
    @Schema(description = "Date of the transaction.", example = "01.01.2001")
    @NotNull(message = "Date of the transaction shouldn't be null.")
    private Timestamp date;
    @NotNull
    private CurrencyDto currency;
    @NotNull
    @NotBlank
    private String type;
    private String message;
    @NotNull
    @PositiveOrZero
    private BigDecimal cashback;
    @Schema(description = "The amount of money that needs to be transferred.", example = "10000")
    @NotNull(message = "The amount of money shouldn't be null.")
    @PositiveOrZero
    private BigDecimal amount;
}
