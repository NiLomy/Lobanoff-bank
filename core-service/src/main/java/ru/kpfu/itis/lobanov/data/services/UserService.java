package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.requests.UpdateUserRequest;

import java.util.List;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface UserService {
    UserDto getById(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    List<UserDto> getAllUsers();

    UserDto getUserByPhone(
            @NotNull(message = PHONE_NOT_NULL)
            @NotBlank(message = PHONE_NOT_BLANK)
            String phone
    );

    UserDto getAdmin();

    UserDto update(
            @NotNull(message = UPDATE_USER_REQUEST_NOT_NULL)
            UpdateUserRequest request
    );

    UserDto banUser(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    UserDto deleteUser(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );
}
