package ru.kpfu.itis.lobanov.api;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.requests.CreateCardRequest;

@Tag(name = "Bank Account Api", description = "Provides methods for bank accounts")
@RequestMapping(path = "/api/v1/cards", produces = "application/json")
@CrossOrigin(origins = "http://localhost:3000")
public interface CardApi {
    @GetMapping("/{id}")
    ResponseEntity<CardDto> getCardById(@PathVariable("id") String cardId);

    @PostMapping("/create")
    ResponseEntity<CardDto> createCard(@RequestBody CreateCardRequest request);
}
