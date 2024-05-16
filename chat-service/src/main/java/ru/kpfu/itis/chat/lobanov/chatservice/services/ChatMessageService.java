package ru.kpfu.itis.chat.lobanov.chatservice.services;

import jakarta.validation.constraints.NotNull;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.MessageStatus;
import ru.kpfu.itis.lobanov.dtos.MessageDto;

import java.util.List;
import java.util.Optional;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ValidationMessages.*;

public interface ChatMessageService {
    Optional<MessageDto> save(
            @NotNull(message = MESSAGE_NOT_NULL)
            MessageDto messageDto
    );

    Optional<MessageDto> getById(
            @NotNull(message = ID_NOT_NULL)
            String id
    );

    List<MessageDto> getChatMessages(
            @NotNull(message = ID_NOT_NULL)
            String senderId
    );

    long countNewMessages(
            @NotNull(message = ID_NOT_NULL)
            String senderId
    );

    void updateStatuses(
            @NotNull(message = ID_NOT_NULL)
            String senderId,
            @NotNull(message = MESSAGE_STATUS_NOT_NULL)
            MessageStatus status
    );
}
