package ru.kpfu.itis.lobanov.data.mappers;

import ru.kpfu.itis.lobanov.data.entities.UserMessage;
import ru.kpfu.itis.lobanov.dtos.UserMessageDto;

import java.util.List;

public interface UserMessageMapper {
    UserMessageDto toResponse(UserMessage userMessage);

    List<UserMessageDto> toListResponse(List<UserMessage> set);
}
