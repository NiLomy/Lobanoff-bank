package ru.kpfu.itis.lobanov.data.services;

import ru.kpfu.itis.lobanov.dtos.*;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.util.List;

public interface TransactionService {
    TransactionDto getById(Long transactionId);

    List<TransactionDto> getAllOperationsFromUser(UserDto userDto);

    List<TransactionDto> getAllRecentTransactions(Long accountId);

    TransactionDto transferByPhone(PhoneTransferForm phoneTransferForm);

    TransactionDto transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm);

    TransactionDto transferByCard(CardTransferForm cardTransferForm);

    TransactionDto transferByAccountDetails(AccountDetailsTransferForm accountDetailsTransferForm);

    TransactionDto replenishByCard(CardReplenishForm cardReplenishForm);
}
