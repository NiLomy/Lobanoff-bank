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
public class PhoneTransferForm {
    @Schema(description = "Id of the bank account to transfer from.", example = "123")
    @NotNull(message = "Account to transfer from is required!")
    private Long from;
    @Schema(description = "The phone number of the bank's client who needs to be transferred.", example = "88005553535")
    @NotBlank(message = "The phone number shouldn't be null.")
    private String phone;
    @Schema(description = "The amount of money that needs to be transferred.", example = "10000")
    @NotNull(message = "The amount of money shouldn't be null.")
    private Long amount;
    @Schema(description = "Message to send to the bank's client who needs to be transferred.", example = "Hello, here is your money!", nullable = true)
    private String message;
}
