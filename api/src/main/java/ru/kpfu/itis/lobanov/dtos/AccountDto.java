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
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {
    @Schema(description = "Id of the bank account.", example = "123")
    private Long id;
    @Schema(description = "Name of the bank account.", example = "Main account")
    @NotNull
    @NotBlank(message = "Name of the bank account shouldn't be empty.")
    private String name;
    @Schema(description = "Deposit of the bank account.", example = "10000")
    @NotNull(message = "Deposit of the bank account shouldn't be empty.")
    @PositiveOrZero
    private BigDecimal deposit;
    @NotNull
    private CurrencyDto currency;
    @NotNull
    @NotBlank
    private String type;
    @Schema(description = "Owner of the bank account.")
    @NotNull(message = "Owner of the bank account shouldn't be null.")
    private UserDto owner;
    @Schema(description = "Cards of the bank account.")
    private List<CardDto> cards;
    @NotNull
    private Boolean main;
}
