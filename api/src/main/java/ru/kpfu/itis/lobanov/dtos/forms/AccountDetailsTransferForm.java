package ru.kpfu.itis.lobanov.dtos.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailsTransferForm {
    @Schema(description = "Contract number of user who needs to be transferred.", example = "1234567890", maxLength = 10)
    @NotBlank(message = "Contract number shouldn't be empty.")
    private String contractNumber;
    @Schema(description = "Id of the bank account to transfer from.", example = "123")
    @NotNull(message = "Account to transfer from is required!")
    private Long from;
    @Schema(description = "The amount of money that needs to be transferred.", example = "10000")
    @NotNull(message = "The amount of money shouldn't be null.")
    private Long amount;
    @Schema(description = "Message to send to the bank's client who needs to be transferred.", example = "Hello, here is your money!", nullable = true)
    private String message;
}
