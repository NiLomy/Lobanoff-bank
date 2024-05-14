package ru.kpfu.itis.chat.lobanov.chatservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ValidationMessages.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document
public class ChatMessage {
    @Id
    private String id;
    @NotNull(message = ID_NOT_NULL)
    private String chatId;
    @NotNull(message = ID_NOT_NULL)
    private String senderId;
    @NotNull(message = ID_NOT_NULL)
    private String recipientId;
    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String senderName;
    @NotNull(message = NAME_NOT_NULL)
    @NotBlank(message = NAME_NOT_BLANK)
    private String recipientName;
    @NotNull(message = MESSAGE_CONTENT_NOT_NULL)
    private String content;
    private Date timestamp;
    private MessageStatus status;
}
