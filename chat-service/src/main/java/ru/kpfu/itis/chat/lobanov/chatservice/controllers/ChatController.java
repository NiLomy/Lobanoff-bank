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

import java.util.Date;
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

    @GetMapping("/a")
    public ResponseEntity<?> pm() {
        Optional<MessageDto> saved = chatMessageService.save(MessageDto.builder()
                .senderId("1")
                .isSupport(false)
                .timestamp(new Date())
                .senderName("AAAA")
                .content("bbbb")
                .build());
        if (saved.isPresent()) {
            return new ResponseEntity<>(HttpStatus.PROCESSING);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


    @Override
    public ResponseEntity<?> processMessage(@Payload MessageDto messageDto) {
        Optional<MessageDto> saved = chatMessageService.save(messageDto);
        if (saved.isPresent()) {
            messagingTemplate.convertAndSendToUser(
                    messageDto.getSenderId(), MESSAGE_DESTINATION,
                    saved.get()
            );
            return new ResponseEntity<>(HttpStatus.PROCESSING);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<Long> countNewMessages(String senderId) {
        return new ResponseEntity<>(chatMessageService.countNewMessages(senderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MessageDto>> findChatMessages(String senderId) {
        return new ResponseEntity<>(chatMessageService.getChatMessages(senderId), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MessageDto> findMessage(String id) {
        Optional<MessageDto> message = chatMessageService.getById(id);
        return message.map(messageDto -> new ResponseEntity<>(messageDto, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
