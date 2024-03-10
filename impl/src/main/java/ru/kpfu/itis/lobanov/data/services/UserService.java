package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto getUserByPhone(String phone);

    void banUser(UserDto userDto);

    void deleteUser(Long userId);
}
