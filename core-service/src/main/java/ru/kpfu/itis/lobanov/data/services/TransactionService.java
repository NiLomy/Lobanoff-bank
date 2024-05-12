package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.math.BigDecimal;
import java.util.List;

@Validated
public interface TransactionService {
    TransactionDto getById(@NonNull Long transactionId);

    List<TransactionDto> getAllOperationsFromUser(@NonNull UserDto userDto);

    List<TransactionDto> getAllRecentTransactions(@NonNull Long accountId);

    TransactionDto transferByPhone(@NonNull PhoneTransferForm phoneTransferForm);

    TransactionDto transferBetweenAccounts(@NonNull BetweenAccountsTransferForm betweenAccountsTransferForm);

    TransactionDto transferByCard(@NonNull CardTransferForm cardTransferForm);

    TransactionDto transferByAccountDetails(@NonNull AccountDetailsTransferForm accountDetailsTransferForm);

    TransactionDto replenishByCard(@NonNull CardReplenishForm cardReplenishForm);

    void chargeInterestTransaction(@NotNull Account account, @NotNull BigDecimal amount);
}
