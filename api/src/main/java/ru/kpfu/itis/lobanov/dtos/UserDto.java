package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Schema(description = "Id of the user.", example = "123")
    private Long id;
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
    @Schema(description = "Email of the user.", example = "test@mail.com")
    @NotNull(message = "User email should not be null.")
    @NotBlank(message = "User email should not be empty.")
    @Email(message = "Email should be valid.")
    private String email;
    @Schema(description = "The phone number of the user.", example = "88005553535")
    private String phone;
    @Schema(description = "Role of the user.")
    @NotNull(message = "User role should not be null.")
    private Role role;
    @Schema(description = "Has the user been deleted.")
    @NotNull(message = "User isDeleted should not be bull.")
    private Boolean isDeleted;
}
