package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.CardDto;

public interface CardService {
    CardDto create(Long accountId);
}
