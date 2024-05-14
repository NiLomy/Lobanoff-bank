package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    @Schema(description = "Id of the card.", example = "123")
    private Long id;
    @NotNull
    @NotBlank
    private String name;
    @Schema(description = "Number of the card.", example = "1144485810352102", maxLength = 16)
    @NotNull
    @NotBlank(message = "Credit card number shouldn't be empty.")
    private String number;
    @Schema(description = "Owner of the bank account.")
    @NotNull(message = "Owner of the card shouldn't be null.")
    private UserDto owner;
    @NotNull
    @PositiveOrZero
    private Long accountId;
}
