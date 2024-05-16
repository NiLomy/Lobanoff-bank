package ru.kpfu.itis.chat.lobanov.chatservice.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.ChatMessage;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.MessageStatus;

import java.util.List;

@Repository
public interface ChatMessageRepository extends MongoRepository<ChatMessage, String> {
    long countBySenderIdAndStatus(String senderId, MessageStatus status);

    List<ChatMessage> findAllBySenderId(String senderId);
}
