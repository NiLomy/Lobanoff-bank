package ru.kpfu.itis.chat.lobanov.chatservice.services;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Optional;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ValidationMessages.ID_NOT_NULL;

@Validated
public interface ChatRoomService {
    Optional<String> getChatId(
            @NotNull(message = ID_NOT_NULL)
            String senderId,
            @NotNull(message = ID_NOT_NULL)
            String recipientId,
            boolean createIfNotExist
    );
}
