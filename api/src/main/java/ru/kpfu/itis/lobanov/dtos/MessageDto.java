package ru.kpfu.itis.lobanov.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDto {
    private String id;
    @NotNull
    @NotBlank
    private String senderId;
    @NotNull
    @NotBlank
    private String senderName;
    @NotNull
    @NotBlank
    private String content;
    @NotNull
    private Boolean isSupport;
    @NotNull
    private Date timestamp;
    private String status;
}
