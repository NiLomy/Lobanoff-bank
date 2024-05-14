package ru.kpfu.itis.lobanov.dtos.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public final class LoginForm {
    @Schema(description = "Email of the user.", example = "test@mail.com")
    @NotNull(message = "Email should not be null.")
    @NotBlank(message = "Email should not be empty.")
    @Email(message = "Email should be valid.")
    private String email;
    @Schema(description = "Password of the user.", example = "password")
    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be empty.")
    private String password;
}
