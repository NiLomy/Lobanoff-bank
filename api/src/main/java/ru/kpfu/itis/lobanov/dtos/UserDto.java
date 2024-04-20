package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Schema(description = "Id of the user.", example = "123")
    private Long id;
    private String name;
    private String lastname;
    private String patronymic;
    @Schema(description = "Email of the user.", example = "test@mail.com")
    @NotBlank(message = "Email shouldn't be empty.")
    @Email(message = "Email should be valid.")
    private String email;
    @Schema(description = "The phone number of the user.", example = "88005553535")
    @NotBlank(message = "The phone number shouldn't be null.")
    private String phone;
    @Schema(description = "Role of the user.")
    @NotNull(message = "Role shouldn't be null.")
    private Role role;
    @Schema(description = "Has the user been deleted.")
    @NotNull(message = "Is deleted flag shouldn't be null.")
    private Boolean isDeleted;
}
