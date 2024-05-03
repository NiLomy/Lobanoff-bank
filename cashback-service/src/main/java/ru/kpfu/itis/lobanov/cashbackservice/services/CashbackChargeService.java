package ru.kpfu.itis.lobanov.cashbackservice.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.cashbackservice.entities.Transaction;

public interface CashbackChargeService {
    void chargeCashback(@NonNull Transaction transaction);
}
