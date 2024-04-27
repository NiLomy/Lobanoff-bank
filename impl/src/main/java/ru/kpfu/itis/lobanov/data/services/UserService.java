package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

public interface UserService {
    UserDto getById(Long userId);

    List<UserDto> getAllUsers();

    UserDto getUserByPhone(String phone);

    UserDto getCurrentUser();

    UserDto register(RegistrationForm registrationForm);

    UserDto banUser(Long userId);

    UserDto deleteUser(Long userId);
}
