package ru.kpfu.itis.lobanov.finalservice.services;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.finalservice.entities.Transaction;

import static ru.kpfu.itis.lobanov.finalservice.utils.ValidationMessages.TRANSACTION_NOT_NULL;

@Validated
public interface TransactionProcessingService {
    void process(
            @NotNull(message = TRANSACTION_NOT_NULL)
            Transaction transaction
    );
}
