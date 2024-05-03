package ru.kpfu.itis.lobanov.cashbackservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    public void chargeCashback(@NonNull Transaction transaction) {
        Long categoryId = transaction.getCategory().getId();
        CashbackCategoryDto cashbackCategory = cashbackCategoryService.getByCategoryId(categoryId);
        if (cashbackCategory == null) throw new IllegalArgumentException();
        BigDecimal cashback = transaction.getInitAmount().subtract(transaction.getCommission()).multiply(cashbackCategory.getCashbackPercentage());
        transaction.setCashback(cashback);
        messagingService.sendTransactionToFinalService(transaction);
    }
}
