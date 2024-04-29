package ru.kpfu.itis.chat.lobanov.chatservice.services;

import ru.kpfu.itis.chat.lobanov.chatservice.entities.MessageStatus;
import ru.kpfu.itis.lobanov.dtos.MessageDto;

import java.util.List;
import java.util.Optional;

public interface ChatMessageService {
    Optional<MessageDto> save(MessageDto messageDto);

    Optional<MessageDto> getById(String id);

    List<MessageDto> getChatMessages(String senderId, String recipientId);

    long countNewMessages(String senderId, String recipientId);

    void updateStatuses(String senderId, String recipientId, MessageStatus status);
}
