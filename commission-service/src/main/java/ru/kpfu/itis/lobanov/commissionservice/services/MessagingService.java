package ru.kpfu.itis.lobanov.commissionservice.services;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;

public interface MessagingService {
    void sendTransactionToChargeCashback(@NotNull @Valid Transaction transaction);
}
