package ru.kpfu.itis.lobanov.cashbackservice.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.dtos.CashbackCategoryDto;

public interface CashbackCategoryService {
    CashbackCategoryDto getByCategoryId(@NonNull Long categoryId);
}
