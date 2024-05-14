package ru.kpfu.itis.lobanov.cashbackservice.services;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.cashbackservice.entities.Transaction;

import static ru.kpfu.itis.lobanov.cashbackservice.utils.ValidationMessages.TRANSACTION_NOT_NULL;

@Validated
public interface MessagingService {
    void sendTransactionToFinalService(
            @NotNull(message = TRANSACTION_NOT_NULL)
            Transaction transaction
    );
}
