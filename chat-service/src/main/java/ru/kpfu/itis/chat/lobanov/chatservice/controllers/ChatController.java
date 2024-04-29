package ru.kpfu.itis.chat.lobanov.chatservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.chat.lobanov.chatservice.services.ChatMessageService;
import ru.kpfu.itis.lobanov.api.ChatApi;
import ru.kpfu.itis.lobanov.dtos.MessageDto;

import java.util.List;
import java.util.Optional;

import static ru.kpfu.itis.chat.lobanov.chatservice.utils.ChatServiceConstants.MESSAGE_DESTINATION;

@Controller
@RequiredArgsConstructor
public class ChatController implements ChatApi {
    private final SimpMessagingTemplate messagingTemplate;
    private final ChatMessageService chatMessageService;

    @GetMapping("/ch")
    public String getPage(Model model) {
        model.addAttribute("currentUser", "123");
        return "test";
    }

    @Override
    public ResponseEntity<?> processMessage(@Payload MessageDto messageDto) {
        Optional<MessageDto> saved = chatMessageService.save(messageDto);
        if (saved.isPresent()) {
            messagingTemplate.convertAndSendToUser(
                    messageDto.getRecipientId(), MESSAGE_DESTINATION,
                    saved.get()
            );
            return new ResponseEntity<>(HttpStatus.PROCESSING);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Long> countNewMessages(String senderId, String recipientId) {
        return new ResponseEntity<>(chatMessageService.countNewMessages(senderId, recipientId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MessageDto>> findChatMessages(String senderId, String recipientId) {
        return new ResponseEntity<>(chatMessageService.getChatMessages(senderId, recipientId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageDto> findMessage(String id) {
        Optional<MessageDto> message = chatMessageService.getById(id);
        return message.map(messageDto -> new ResponseEntity<>(messageDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
