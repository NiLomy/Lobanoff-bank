package ru.kpfu.itis.lobanov.commissionservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;
import ru.kpfu.itis.lobanov.commissionservice.services.CommissionChargeService;
import ru.kpfu.itis.lobanov.commissionservice.services.MessagingService;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CommissionChargeServiceImpl implements CommissionChargeService {
    public static final int AMOUNT_THRESHOLD = 100_000;
    public static final double COMMISSION_MULTIPLIER = 0.05;
    private final MessagingService messagingService;

    @Override
    public void chargeCommission(Transaction transaction) {
        if (transaction.getInitAmount().compareTo(BigDecimal.valueOf(AMOUNT_THRESHOLD)) >= 0) {
            transaction.setCommission(transaction.getInitAmount().multiply(BigDecimal.valueOf(COMMISSION_MULTIPLIER)));
        } else {
            transaction.setCommission(BigDecimal.ZERO);
        }
        messagingService.sendTransactionToChargeCashback(transaction);
    }
}
