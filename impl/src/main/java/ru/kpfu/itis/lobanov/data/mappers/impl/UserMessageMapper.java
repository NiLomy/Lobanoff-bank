package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.entities.UserMessage;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.UserMessageDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UserMessageMapper implements Mapper<UserMessage, UserMessageDto> {
    private final Mapper<User, UserDto> userMapper;

    @Override
    public UserMessageDto toResponse(UserMessage userMessage) {
        return UserMessageDto.builder()
                .id(userMessage.getId())
                .content(userMessage.getContent())
                .author(userMapper.toResponse(userMessage.getAuthor()))
                .createdAt(userMessage.getCreatedAt())
                .repliedMessageId(userMessage.getRepliedOn().getId())
                .build();
    }

    @Override
    public List<UserMessageDto> toListResponse(List<UserMessage> set) {
        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
