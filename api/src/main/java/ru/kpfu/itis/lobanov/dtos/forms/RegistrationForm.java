package ru.kpfu.itis.lobanov.dtos.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class RegistrationForm {
    @NotBlank
    private String name;
    @NotBlank
    private String lastname;
    @NotBlank
    private String patronymic;
    @Schema(description = "Email of the new client.", example = "test@mail.com")
    @NotBlank(message = "Email shouldn't be empty.")
    @Email(message = "Email should be valid.")
    private String email;
    @Schema(description = "Password of the new client.")
    @NotBlank(message = "Password shouldn't be empty.")
    private String password;
    @Schema(description = "Confirmed password of the new client.")
    @NotBlank(message = "Confirmed password shouldn't be empty.")
    private String confirmPassword;
    private String url;
}
