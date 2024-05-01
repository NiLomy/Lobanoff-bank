package ru.kpfu.itis.lobanov.data.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper implements Mapper<User, UserDto> {

    @Override
    public UserDto toResponse(User userEntity) {
        if ( userEntity == null ) {
            return null;
        }

        return UserDto.builder()
                .id(userEntity.getId())
                .name(userEntity.getName())
                .lastname(userEntity.getLastname())
                .patronymic(userEntity.getPatronymic())
                .email(userEntity.getEmail())
                .phone(userEntity.getPhone())
                .role(userEntity.getRole())
                .isDeleted(userEntity.getDeleted())
                .build();
    }

    @Override
    public List<UserDto> toListResponse(List<User> set) {
        if ( set == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( set.size() );
        for ( User user : set ) {
            list.add( toResponse( user ) );
        }

        return list;
    }
}
