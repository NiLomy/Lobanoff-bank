package ru.kpfu.itis.lobanov.finalservice.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.lobanov.finalservice.entities.Transaction;
import ru.kpfu.itis.lobanov.finalservice.repositories.TransactionRepository;
import ru.kpfu.itis.lobanov.finalservice.services.TransactionProcessingService;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static ru.kpfu.itis.lobanov.finalservice.utils.ValueConstants.*;

@Service
@RequiredArgsConstructor
public class TransactionProcessingServiceImpl implements TransactionProcessingService {
    private final TransactionRepository transactionRepository;

    @Override
    public void process(Transaction transaction) {
        processRiskIndicator(transaction);
        processTotalAmount(transaction);
        transactionRepository.save(transaction);
    }

    private void processRiskIndicator(Transaction transaction) {
        BigDecimal initAmount = transaction.getInitAmount();
        BigDecimal avgAmount = transactionRepository.calcAverageAmount();
        BigDecimal maxProbability = BigDecimal.valueOf(MAX_PROBABILITY);
        BigDecimal risk = initAmount.divide(avgAmount.multiply(BigDecimal.valueOf(RISK_MULTIPLIER)), RISK_SCALE, RoundingMode.HALF_EVEN).multiply(maxProbability);
        if (risk.compareTo(maxProbability) > 0) {
            risk = maxProbability;
        }
        transaction.setRiskIndicator(risk.toBigInteger().intValue());
    }

    private void processTotalAmount(Transaction transaction) {
        BigDecimal initAmount = transaction.getInitAmount();
        BigDecimal commission = transaction.getCommission();
        BigDecimal cashback = transaction.getCashback();
        if (cashback == null) cashback = BigDecimal.ZERO;
        BigDecimal total = initAmount.subtract(commission).add(cashback);
        transaction.setTotalAmount(total);
    }
}
