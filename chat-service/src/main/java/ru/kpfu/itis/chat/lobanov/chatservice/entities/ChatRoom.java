package ru.kpfu.itis.chat.lobanov.chatservice.entities;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ValidationMessages.ID_NOT_NULL;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ChatRoom {
    @Id
    private String id;
    @NotNull(message = ID_NOT_NULL)
    private String chatId;
    @NotNull(message = ID_NOT_NULL)
    private String senderId;
    @NotNull(message = ID_NOT_NULL)
    private String recipientId;
}
