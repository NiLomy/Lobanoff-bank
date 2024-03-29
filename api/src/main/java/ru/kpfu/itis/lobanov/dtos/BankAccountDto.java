package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDto {
    @Schema(description = "Id of the bank account.", example = "123")
    private Long id;
    @Schema(description = "Name of the bank account.", example = "Main account")
    @NotBlank(message = "Name of the bank account shouldn't be empty.")
    private String name;
    @Schema(description = "Deposit of the bank account.", example = "10000")
    @NotNull(message = "Deposit of the bank account shouldn't be empty.")
    private Long deposit;
    @Schema(description = "Owner of the bank account.")
    @NotNull(message = "Owner of the bank account shouldn't be null.")
    private UserDto owner;
    @Schema(description = "Operations of the bank account.")
    private List<OperationDto> operations;
    @Schema(description = "Cards of the bank account.")
    private List<CardDto> cards;
}
