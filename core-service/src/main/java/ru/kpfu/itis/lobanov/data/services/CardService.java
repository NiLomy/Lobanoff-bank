package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.requests.CreateCardRequest;

public interface CardService {
    CardDto getById(Long cardId);

    CardDto create(CreateCardRequest request);
}
