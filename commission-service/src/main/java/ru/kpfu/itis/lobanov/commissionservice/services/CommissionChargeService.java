package ru.kpfu.itis.lobanov.commissionservice.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;

public interface CommissionChargeService {
    void chargeCommission(@NotNull @Valid Transaction transaction);
}
