package ru.kpfu.itis.lobanov.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import ru.kpfu.itis.lobanov.api.CardApi;
import ru.kpfu.itis.lobanov.data.services.CardService;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.requests.CreateCardRequest;

@RestController
@RequiredArgsConstructor
public class CardController implements CardApi {
    private final CardService cardService;

    @Override
    public ResponseEntity<CardDto> getCardById(String cardId) {
        try {
            CardDto card = cardService.getById(Long.parseLong(cardId));

            if (card == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public ResponseEntity<CardDto> createCard(CreateCardRequest request) {
        try {
            CardDto card = cardService.create(request);

            if (card == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            return new ResponseEntity<>(card, HttpStatus.OK);
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
