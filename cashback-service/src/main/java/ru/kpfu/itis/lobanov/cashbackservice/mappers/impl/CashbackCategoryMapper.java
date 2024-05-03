package ru.kpfu.itis.lobanov.cashbackservice.mappers.impl;

import org.springframework.stereotype.Component;
import ru.kpfu.itis.lobanov.cashbackservice.entities.CashbackCategory;
import ru.kpfu.itis.lobanov.cashbackservice.mappers.Mapper;
import ru.kpfu.itis.lobanov.dtos.CashbackCategoryDto;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CashbackCategoryMapper implements Mapper<CashbackCategory, CashbackCategoryDto> {

    @Override
    public CashbackCategoryDto toResponse(CashbackCategory entity) {
        if (entity == null) return null;

        return CashbackCategoryDto.builder()
                .categoryId(entity.getCategoryId())
                .cashbackPercentage(entity.getCashbackPercentage())
                .build();
    }

    @Override
    public List<CashbackCategoryDto> toListResponse(List<CashbackCategory> set) {
        if (set == null) return null;

        return set.stream().map(this::toResponse).collect(Collectors.toList());
    }
}
