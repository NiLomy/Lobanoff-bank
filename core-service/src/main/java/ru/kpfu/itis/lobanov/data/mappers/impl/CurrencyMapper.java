package ru.kpfu.itis.lobanov.data.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.BankInfo;
import ru.kpfu.itis.lobanov.data.entities.Currency;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.BankInfoDto;
import ru.kpfu.itis.lobanov.dtos.CurrencyDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CurrencyMapper implements Mapper<Currency, CurrencyDto> {

    @Override
    public CurrencyDto toResponse(Currency entity) {
        if (entity == null) return null;

        return CurrencyDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .icon(entity.getIcon())
                .build();
    }

    @Override
    public List<CurrencyDto> toListResponse(List<Currency> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
