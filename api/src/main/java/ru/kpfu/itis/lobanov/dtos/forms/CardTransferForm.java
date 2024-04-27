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
public class CardTransferForm {
    @Schema(description = "Id of the bank account to transfer from.", example = "123")
    @NotNull(message = "Account to transfer from is required!")
    private Long from;
    @Schema(description = "Credit card number of the bank's client who needs to be transferred.", example = "1144485810352102", maxLength = 16)
    @NotBlank(message = "Credit card number shouldn't be empty.")
    private String card;
    @Schema(description = "The amount of money that needs to be transferred.", example = "10000")
    @NotNull(message = "The amount of money shouldn't be null.")
    private Long amount;
}
