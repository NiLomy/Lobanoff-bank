package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.MessageDto;

import java.util.List;

@Tag(name = "Chat Api", description = "Provides methods for users chatting")
@RequestMapping(path = "/api/v1/chat", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface ChatApi {
    @MessageMapping("/chat")
    ResponseEntity<?> processMessage(@Payload MessageDto messageDto);

    @GetMapping("/messages/{senderId}/{recipientId}/count")
    ResponseEntity<Long> countNewMessages(@PathVariable String senderId, @PathVariable String recipientId);

    @GetMapping("/messages/{senderId}/{recipientId}")
    ResponseEntity<List<MessageDto>> findChatMessages(@PathVariable String senderId, @PathVariable String recipientId);

    @GetMapping("/messages/{id}")
    ResponseEntity<MessageDto> findMessage(@PathVariable String id);
}
