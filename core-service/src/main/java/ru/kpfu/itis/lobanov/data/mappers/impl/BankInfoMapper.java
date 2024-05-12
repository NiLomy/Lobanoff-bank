package ru.kpfu.itis.lobanov.data.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.data.entities.CardInfo;
import ru.kpfu.itis.lobanov.data.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.BankInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BankInfoMapper implements Mapper<CardInfo, BankInfoDto> {

    @Override
    public BankInfoDto toResponse(CardInfo entity) {
        if (entity == null) return null;

        return BankInfoDto.builder()
                .bin(entity.getBin())
                .brand(entity.getBrand())
                .category(entity.getCategory())
                .issuer(entity.getIssuer())
                .type(entity.getType())
                .alpha2(entity.getAlpha2())
                .alpha3(entity.getAlpha3())
                .country(entity.getCountry())
                .build();
    }

    @Override
    public List<BankInfoDto> toListResponse(List<CardInfo> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
