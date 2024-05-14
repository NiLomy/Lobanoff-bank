package ru.kpfu.itis.lobanov.commissionservice.services;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;

import static ru.kpfu.itis.lobanov.commissionservice.utils.ValidationMessages.TRANSACTION_NOT_NULL;

@Validated
public interface CommissionChargeService {
    void chargeCommission(
            @NotNull(message = TRANSACTION_NOT_NULL)
            Transaction transaction
    );
}
