package ru.kpfu.itis.lobanov.finalservice.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.finalservice.entities.Transaction;

public interface TransactionProcessingService {
    void process(@NonNull Transaction transaction);
}
