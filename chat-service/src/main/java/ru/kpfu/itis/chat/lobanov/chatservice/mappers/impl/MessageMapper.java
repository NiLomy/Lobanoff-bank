package ru.kpfu.itis.chat.lobanov.chatservice.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.ChatMessage;
import ru.kpfu.itis.chat.lobanov.chatservice.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.MessageDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper implements Mapper<ChatMessage, MessageDto> {
    @Override
    public MessageDto toResponse(ChatMessage entity) {
        if (entity == null) return null;

        return MessageDto.builder()
                .id(entity.getId())
                .senderId(entity.getSenderId())
                .recipientId(entity.getRecipientId())
                .senderName(entity.getSenderName())
                .recipientName(entity.getRecipientName())
                .content(entity.getContent())
                .timestamp(entity.getTimestamp())
                .build();
    }

    @Override
    public List<MessageDto> toListResponse(List<ChatMessage> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
