package ru.kpfu.itis.chat.lobanov.chatservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.chat.lobanov.chatservice.entities.ChatRoom;
import ru.kpfu.itis.chat.lobanov.chatservice.repositories.ChatRoomRepository;
import ru.kpfu.itis.chat.lobanov.chatservice.services.ChatRoomService;

import java.util.Optional;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ChatServiceConstants.CHAT_ID_TEMPLATE;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;

    @Override
    public Optional<String> getChatId(String senderId, String recipientId, boolean createIfNotExist) {
        return chatRoomRepository
                .findBySenderIdAndRecipientId(senderId, recipientId)
                .map(ChatRoom::getChatId)
                .or(() -> {
                    if (!createIfNotExist) {
                        return Optional.empty();
                    }
                    String chatId = String.format(CHAT_ID_TEMPLATE, senderId, recipientId);

                    ChatRoom senderRecipient = ChatRoom.builder()
                            .chatId(chatId)
                            .senderId(senderId)
                            .recipientId(recipientId)
                            .build();

                    ChatRoom recipientSender = ChatRoom.builder()
                            .chatId(chatId)
                            .senderId(recipientId)
                            .recipientId(senderId)
                            .build();

                    chatRoomRepository.save(senderRecipient);
                    chatRoomRepository.save(recipientSender);

                    return Optional.of(chatId);
                });
    }
}
