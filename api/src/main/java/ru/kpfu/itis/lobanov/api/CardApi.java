package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.requests.CreateCardRequest;

@Tag(name = "Card Api", description = "Provides methods for banking cards")
@RequestMapping(path = "/api/v1/cards", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface CardApi {
    @Operation(summary = "Get card by id.", description = "Returns card by its id.", responses = {
            @ApiResponse(responseCode = "200", description = "Operation was successful."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @GetMapping("/{id}")
    ResponseEntity<CardDto> getCardById(
            @Parameter(description = "Id of the card.", example = "123", required = true)
            @PathVariable("id")
            String cardId
    );

    @Operation(summary = "Create card.", description = "Returns created card.", responses = {
            @ApiResponse(responseCode = "201", description = "Card was successfully created."),
            @ApiResponse(responseCode = "400", description = "Bad request."),
            @ApiResponse(responseCode = "500", description = "The server encountered an error.")
    })
    @PostMapping("/create")
    ResponseEntity<CardDto> createCard(
            @Parameter(description = "Request for creating a card.", required = true)
            @RequestBody
            CreateCardRequest request
    );
}
