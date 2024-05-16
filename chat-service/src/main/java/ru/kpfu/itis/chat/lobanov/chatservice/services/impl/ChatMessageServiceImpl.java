package ru.kpfu.itis.chat.lobanov.chatservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.ChatMessage;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.MessageStatus;
import ru.kpfu.itis.chat.lobanov.chatservice.mappers.Mapper;
import ru.kpfu.itis.chat.lobanov.chatservice.repositories.ChatMessageRepository;
import ru.kpfu.itis.chat.lobanov.chatservice.services.ChatMessageService;
import ru.kpfu.itis.chat.lobanov.chatservice.services.ChatRoomService;
import ru.kpfu.itis.lobanov.dtos.MessageDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ChatServiceConstants.*;

@Service
@RequiredArgsConstructor
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final MongoOperations mongoOperations;
    private final Mapper<ChatMessage, MessageDto> messageMapper;

    @Override
    public Optional<MessageDto> save(MessageDto messageDto) {
        ChatMessage message = ChatMessage.builder()
                .senderId(messageDto.getSenderId())
                .senderName(messageDto.getSenderName())
                .content(messageDto.getContent())
                .timestamp(messageDto.getTimestamp())
                .status(MessageStatus.RECEIVED)
                .isSupport(messageDto.getIsSupport())
                .build();
        return Optional.of(messageMapper.toResponse(chatMessageRepository.save(message)));
    }

    @Override
    public Optional<MessageDto> getById(String id) {
        return Optional.ofNullable(messageMapper.toResponse(
                chatMessageRepository.findById(id).map(chatMessage -> {
                    chatMessage.setStatus(MessageStatus.DELIVERED);
                    return chatMessageRepository.save(chatMessage);
                }).orElse(null))
        );
    }

    @Override
    public List<MessageDto> getChatMessages(String senderId) {
        List<ChatMessage> messages = chatMessageRepository.findAllBySenderId(senderId);

        if (!messages.isEmpty()) {
            updateStatuses(senderId, MessageStatus.DELIVERED);
        }

        return messageMapper.toListResponse(messages);
    }

    @Override
    public long countNewMessages(String senderId) {
        return chatMessageRepository.countBySenderIdAndStatus(senderId, MessageStatus.RECEIVED);
    }

    @Override
    public void updateStatuses(String senderId, MessageStatus status) {
        Query query = new Query(
                Criteria
                        .where(SENDER_ID_KEY).is(senderId));
        Update update = Update.update(STATUS_KEY, status);
        mongoOperations.updateMulti(query, update, ChatMessage.class);
    }
}
