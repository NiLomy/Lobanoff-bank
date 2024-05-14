package ru.kpfu.itis.lobanov.dtos.forms;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.lang.reflect.Method;

@Data
public final class RegistrationForm {
    @NotNull(message = "User name should not be null.")
    @NotBlank(message = "User name should not be empty.")
    @Schema(description = "Name of the user.", example = "Ivan")
    private String name;
    @NotNull(message = "User lastname should not be null.")
    @NotBlank(message = "User lastname should not be empty.")
    @Schema(description = "Lastname of the user.", example = "Ivanov")
    private String lastname;
    @Schema(description = "Patronymic of the user.", example = "Jovanovich")
    private String patronymic;
    @Schema(description = "Passport series.", example = "1234")
    @NotNull(message = "Series should not be null.")
    @PositiveOrZero(message = "Series should be positive.")
    private Short series;
    @Schema(description = "Passport number.", example = "567890")
    @NotNull(message = "Number should not be null.")
    private Integer number;
    @Schema(description = "User's birthday.", example = "01.01.2000")
    @NotNull
    @NotBlank
    private String birthday;
    @NotNull
    private Character gender;
    @NotNull
    @NotBlank
    private String departmentCode;
    @NotNull
    @NotBlank
    private String issuedBy;
    @NotNull
    @NotBlank
    private String issuedDate;
    @NotNull
    @NotBlank
    private String address;
    @Schema(description = "Email of the user.", example = "test@mail.com")
    @NotNull(message = "User email should not be null.")
    @NotBlank(message = "User email should not be empty.")
    @Email(message = "Email should be valid.")
    private String email;
    @Schema(description = "The phone number of the user.", example = "88005553535")
    private String phone;
    @Schema(description = "Password of the user.", example = "password")
    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be empty.")
    private String password;
    @Schema(description = "Confirmed password of the new client.", example = "password")
    @NotNull(message = "Password should not be null.")
    @NotBlank(message = "Password should not be empty.")
    private String confirmPassword;
    private String url;
}
