package ru.kpfu.itis.lobanov.commissionservice.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;

public interface CommissionChargeService {
    void chargeCommission(@NonNull Transaction transaction);
}
