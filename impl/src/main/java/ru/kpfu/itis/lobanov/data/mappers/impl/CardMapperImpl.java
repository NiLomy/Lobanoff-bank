package ru.kpfu.itis.lobanov.data.mappers.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.mappers.CardMapper;
import ru.kpfu.itis.lobanov.data.mappers.UserMapper;
import ru.kpfu.itis.lobanov.dtos.CardDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CardMapperImpl implements CardMapper {
    private final UserMapper userMapper;

    @Override
    public CardDto toResponse(Card card) {
        return CardDto.builder()
                .id(card.getId())
                .number(card.getNumber())
                .owner(userMapper.toResponse(card.getOwner()))
                .build();
    }

    @Override
    public List<CardDto> toListResponse(List<Card> set) {
        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
