package ru.kpfu.itis.lobanov.commissionservice.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.commissionservice.entities.Transaction;

public interface MessagingService {
    void sendTransactionToChargeCashback(@NonNull Transaction transaction);
}
