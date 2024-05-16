package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardMapper implements Mapper<Card, CardDto> {
    private final Mapper<User, UserDto> userMapper;

    @Override
    public CardDto toResponse(Card card) {
        if (card == null) return null;

        return CardDto.builder()
                .id(card.getId())
                .number(card.getNumber())
                .expiration(card.getExpiration())
                .cvv(card.getCvv())
                .accountId(card.getAccount().getId())
                .build();
    }

    @Override
    public List<CardDto> toListResponse(List<Card> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
