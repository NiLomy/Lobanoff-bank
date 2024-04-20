package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.CardDto;

public interface CardService {
    CardDto getById(Long cardId);

    CardDto create(Long accountId);
}
