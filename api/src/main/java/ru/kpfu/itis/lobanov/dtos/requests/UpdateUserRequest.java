package ru.kpfu.itis.lobanov.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public final class UpdateUserRequest {
    @NotNull
    @NotBlank
    private String id;
    @NotNull
    @NotBlank
    @Email
    private String email;
    private String phone;
    private String password;
    private String confirmPassword;
}
