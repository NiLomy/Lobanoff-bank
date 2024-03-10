package ru.kpfu.itis.lobanov.data.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationForm {
    @NotBlank(message = "Email is required!")
    @Email(message = "Email should be normal!")
    private String email;
    @NotBlank(message = "Password is required!")
    private String password;
    @NotBlank(message = "Confirmed password is required!")
    private String confirmPassword;

    public User toUser(PasswordEncoder passwordEncoder) {
        return User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .role(Role.USER)
                .state(State.ACTIVE)
                .isDeleted(false)
                .build();
    }
}
