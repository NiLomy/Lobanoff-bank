package ru.kpfu.itis.lobanov.data.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.data.entities.Transaction;

public interface MessagingService {
    void sendTransactionToChargeCommission(@NonNull Transaction transaction);

    void sendEmail(@NonNull String mail, @NonNull String name, @NonNull String code);
}
