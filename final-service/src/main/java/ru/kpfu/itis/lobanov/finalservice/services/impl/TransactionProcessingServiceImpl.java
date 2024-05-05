package ru.kpfu.itis.lobanov.finalservice.services.impl;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.finalservice.entities.Transaction;
import ru.kpfu.itis.lobanov.finalservice.repositories.TransactionRepository;
import ru.kpfu.itis.lobanov.finalservice.services.TransactionProcessingService;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class TransactionProcessingServiceImpl implements TransactionProcessingService {
    private final TransactionRepository transactionRepository;

    @Override
    public void process(@NonNull Transaction transaction) {
        processRiskIndicator(transaction);
        processTotalAmount(transaction);
        transactionRepository.save(transaction);
    }

    private void processRiskIndicator(@NonNull Transaction transaction) {
        BigDecimal initAmount = transaction.getInitAmount();
        BigDecimal avgAmount = transactionRepository.calcAverageAmount();
        BigDecimal maxProbability = BigDecimal.valueOf(100);
        BigDecimal risk = initAmount.divide(avgAmount.multiply(BigDecimal.valueOf(20)), 2, RoundingMode.HALF_EVEN).multiply(maxProbability);
        if (risk.compareTo(maxProbability) > 0) {
            risk = maxProbability;
        }
        transaction.setRiskIndicator(risk.toBigInteger().intValue());
    }

    private void processTotalAmount(@NonNull Transaction transaction) {
        BigDecimal initAmount = transaction.getInitAmount();
        BigDecimal commission = transaction.getCommission();
        BigDecimal cashback = transaction.getCashback();
        if (cashback == null) cashback = BigDecimal.ZERO;
        BigDecimal total = initAmount.multiply(BigDecimal.ONE.subtract(commission)).add(cashback);
        transaction.setTotalAmount(total);
    }
}
