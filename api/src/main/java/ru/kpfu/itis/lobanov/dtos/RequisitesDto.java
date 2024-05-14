package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequisitesDto {
    @Schema(description = "Id of the requisites.", example = "123")
    private Long id;
    @Schema(description = "Owner of the bank account.")
    @NotNull(message = "Owner of the account shouldn't be null.")
    private UserDto payee;
    @Schema(description = "Number of the account.", example = "11444858103521021010", maxLength = 20)
    @NotNull
    @NotBlank(message = "Account number shouldn't be empty.")
    private String accountNumber;
    @Schema(description = "Bank identification code.", example = "858103521", maxLength = 9)
    @NotNull
    @NotBlank(message = "Code number shouldn't be empty.")
    private String code;
    @Schema(description = "Name of the bank.", example = "Lobanoff bank")
    @NotNull
    @NotBlank(message = "Bank name shouldn't be empty.")
    private String bankName;
    @Schema(description = "Number of the correspondent account.", example = "11444858103521021010", maxLength = 20)
    @NotNull
    @NotBlank(message = "Account number shouldn't be empty.")
    private String corrAccount;
}
