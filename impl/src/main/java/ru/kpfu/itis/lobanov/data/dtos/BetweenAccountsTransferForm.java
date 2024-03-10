package ru.kpfu.itis.lobanov.data.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BetweenAccountsTransferForm {
    @NotNull(message = "Account to transfer from is required!")
    private Long from;
    @NotNull(message = "Account to transfer to is required!")
    private Long to;
    @NotNull(message = "Amount is required!")
    private Long amount;
}
