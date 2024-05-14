package ru.kpfu.itis.lobanov.cashbackservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.cashbackservice.entities.Category;
import ru.kpfu.itis.lobanov.cashbackservice.entities.Transaction;
import ru.kpfu.itis.lobanov.cashbackservice.services.CashbackCategoryService;
import ru.kpfu.itis.lobanov.cashbackservice.services.CashbackChargeService;
import ru.kpfu.itis.lobanov.cashbackservice.services.MessagingService;
import ru.kpfu.itis.lobanov.dtos.CashbackCategoryDto;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CashbackChargeServiceImpl implements CashbackChargeService {
    private final MessagingService messagingService;
    private final CashbackCategoryService cashbackCategoryService;

    @Override
    public void chargeCashback(Transaction transaction) {
        Category category = transaction.getCategory();
        if (category != null) {
            Long categoryId = category.getId();
            CashbackCategoryDto cashbackCategory = cashbackCategoryService.getByCategoryId(categoryId);
            if (cashbackCategory != null) {
                BigDecimal cashback = transaction.getInitAmount().subtract(transaction.getCommission()).multiply(cashbackCategory.getCashbackPercentage());
                transaction.setCashback(cashback);
            }
        } else {
            transaction.setCashback(BigDecimal.ZERO);
        }
        messagingService.sendTransactionToFinalService(transaction);
    }
}
