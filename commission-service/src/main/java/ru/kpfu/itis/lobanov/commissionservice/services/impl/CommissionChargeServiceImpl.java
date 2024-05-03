package ru.kpfu.itis.lobanov.commissionservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;
import ru.kpfu.itis.lobanov.commissionservice.services.CommissionChargeService;
import ru.kpfu.itis.lobanov.commissionservice.services.MessagingService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CommissionChargeServiceImpl implements CommissionChargeService {
    private final MessagingService messagingService;

    @Override
    public void chargeCommission(@NonNull Transaction transaction) {
        if (transaction.getInitAmount().compareTo(BigDecimal.valueOf(100_000)) >= 0) {
            transaction.setCommission(transaction.getInitAmount().multiply(BigDecimal.valueOf(0.05)));
        } else {
            transaction.setCommission(BigDecimal.ZERO);
        }
        messagingService.sendTransactionToChargeCashback(transaction);
    }
}
