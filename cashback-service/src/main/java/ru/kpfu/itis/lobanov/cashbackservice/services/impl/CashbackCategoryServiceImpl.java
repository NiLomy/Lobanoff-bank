package ru.kpfu.itis.lobanov.cashbackservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.cashbackservice.entities.CashbackCategory;
import ru.kpfu.itis.lobanov.cashbackservice.mappers.Mapper;
import ru.kpfu.itis.lobanov.cashbackservice.repositories.CashbackCategoryRepository;
import ru.kpfu.itis.lobanov.cashbackservice.services.CashbackCategoryService;
import ru.kpfu.itis.lobanov.dtos.CashbackCategoryDto;

@Service
@RequiredArgsConstructor
public class CashbackCategoryServiceImpl implements CashbackCategoryService {
    private final CashbackCategoryRepository cashbackCategoryRepository;
    private final Mapper<CashbackCategory, CashbackCategoryDto> cashbackCategoryMapper;

    @Override
    public CashbackCategoryDto getByCategoryId(Long categoryId) {
        return cashbackCategoryMapper.toResponse(cashbackCategoryRepository.findByCategoryId(categoryId).orElse(null));
    }
}
