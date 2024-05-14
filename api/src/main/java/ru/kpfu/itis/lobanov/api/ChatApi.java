package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Process message.", description = "Processing message.", responses = {
            @ApiResponse(responseCode = "102", description = "Successful processing."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @MessageMapping("/chat")
    ResponseEntity<?> processMessage(
            @Parameter(description = "Message to process.", required = true)
            @Payload
            MessageDto messageDto
    );

    @Operation(summary = "Count new messages.", description = "Returns count of all messages.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Messages not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/messages/{senderId}/{recipientId}/count")
    ResponseEntity<Long> countNewMessages(
            @Parameter(description = "Id of the sender.", example = "123", required = true)
            @PathVariable
            String senderId,
            @Parameter(description = "Id of the recipient.", example = "123", required = true)
            @PathVariable
            String recipientId
    );

    @Operation(summary = "Get all messages in chat.", description = "Returns all messages in a chat.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Messages not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/messages/{senderId}/{recipientId}")
    ResponseEntity<List<MessageDto>> findChatMessages(
            @Parameter(description = "Id of the sender.", example = "123", required = true)
            @PathVariable
            String senderId,
            @Parameter(description = "Id of the recipient.", example = "123", required = true)
            @PathVariable
            String recipientId
    );

    @Operation(summary = "Get message by id.", description = "Returns message by provided id.", responses = {
            @ApiResponse(responseCode = "200", description = "Successful operation."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "404", description = "Message not found."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/messages/{id}")
    ResponseEntity<MessageDto> findMessage(
            @Parameter(description = "Id of the message.", example = "123", required = true)
            @PathVariable
            String id
    );
}
