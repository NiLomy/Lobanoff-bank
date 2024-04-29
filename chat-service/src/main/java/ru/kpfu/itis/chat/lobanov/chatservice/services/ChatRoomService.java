package ru.kpfu.itis.chat.lobanov.chatservice.services;

import java.util.Optional;

public interface ChatRoomService {
    Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist);
}
