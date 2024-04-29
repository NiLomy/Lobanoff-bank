package ru.kpfu.itis.chat.lobanov.chatservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.ChatMessage;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.MessageStatus;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    long countBySenderIdAndRecipientIdAndStatus(String senderId, String recipientId, MessageStatus status);

    List<ChatMessage> findByChatId(String chatId);
}
