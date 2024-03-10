package ru.kpfu.itis.lobanov.data.mappers;

import org.springframework.data.domain.Page;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

public interface UserMapper {
    UserDto toResponse(User userEntity);
    List<UserDto> toListResponse(List<User> set);
}
