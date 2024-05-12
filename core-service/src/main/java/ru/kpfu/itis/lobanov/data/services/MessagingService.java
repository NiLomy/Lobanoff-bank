package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.data.entities.Transaction;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface MessagingService {
    void sendTransactionToChargeCommission(
            @NotNull(message = TRANSACTION_NOT_NULL)
            Transaction transaction
    );

    void sendEmail(
            @NotNull(message = EMAIL_NOT_NULL)
            @NotBlank(message = EMAIL_NOT_BLANK)
            String mail,
            @NotNull(message = NAME_NOT_NULL)
            @NotBlank(message = NAME_NOT_BLANK)
            String name,
            @NotNull(message = URL_NOT_NULL)
            @NotBlank(message = URL_NOT_BLANK)
            String url
    );
}
