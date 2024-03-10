package ru.kpfu.itis.lobanov.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserMessageDto {
    @Schema(description = "Id of the user message.", example = "123")
    private Long id;
    @Schema(description = "Content of the user message.", example = "Hello there!")
    @NotBlank(message = "Content of the message shouldn't be empty.")
    private String content;
    @Schema(description = "Author of the message.")
    @NotBlank(message = "Author of the message shouldn't be empty.")
    private UserDto author;
    @Schema(description = "Date of the user message.", example = "01.01.2001")
    @NotBlank(message = "Date of the message shouldn't be empty.")
    private Date createdAt;
    @Schema(description = "Id of the user message that was answered.", example = "123", nullable = true)
    private Long repliedMessageId;
}
