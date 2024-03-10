package ru.kpfu.itis.lobanov.data.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class UserMessageDto {
    private Long id;

    private String content;

    private UserDto author;

    @NotNull
    private Date createdAt;

    private Long repliedMessageId;
}
