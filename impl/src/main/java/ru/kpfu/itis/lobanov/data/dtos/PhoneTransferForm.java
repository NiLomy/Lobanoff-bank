package ru.kpfu.itis.lobanov.data.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhoneTransferForm {
    @NotBlank(message = "Phone is required!")
    private String phone;
    @NotNull(message = "Amount is required!")
    private Long amount;
    private String message;
}
