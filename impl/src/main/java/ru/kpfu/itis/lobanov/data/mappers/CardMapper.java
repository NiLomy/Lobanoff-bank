package ru.kpfu.itis.lobanov.data.mappers;

import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.dtos.CardDto;

import java.util.List;

public interface CardMapper {
    CardDto toResponse(Card card);

    List<CardDto> toListResponse(List<Card> set);
}
