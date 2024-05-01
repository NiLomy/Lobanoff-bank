package ru.kpfu.itis.lobanov.data.services;

import lombok.NonNull;
import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.util.List;

public interface TransactionService {
    TransactionDto getById(@NonNull Long transactionId);

    List<TransactionDto> getAllOperationsFromUser(@NonNull UserDto userDto);

    List<TransactionDto> getAllRecentTransactions(@NonNull Long accountId);

    TransactionDto transferByPhone(@NonNull PhoneTransferForm phoneTransferForm);

    TransactionDto transferBetweenAccounts(@NonNull BetweenAccountsTransferForm betweenAccountsTransferForm);

    TransactionDto transferByCard(@NonNull CardTransferForm cardTransferForm);

    TransactionDto transferByAccountDetails(@NonNull AccountDetailsTransferForm accountDetailsTransferForm);

    TransactionDto replenishByCard(@NonNull CardReplenishForm cardReplenishForm);
}
