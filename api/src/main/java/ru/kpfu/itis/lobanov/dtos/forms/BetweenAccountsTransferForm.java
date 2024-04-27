package ru.kpfu.itis.lobanov.dtos.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetweenAccountsTransferForm {
    @Schema(description = "Id of the bank account to transfer from.", example = "123")
    @NotNull(message = "Account to transfer from is required!")
    private Long from;
    @Schema(description = "Id of the bank account to transfer to.", example = "123")
    @NotNull(message = "Account to transfer to is required!")
    private Long to;
    @Schema(description = "The amount of money that needs to be transferred.", example = "10000")
    @NotNull(message = "The amount of money shouldn't be null.")
    private Long amount;
}
