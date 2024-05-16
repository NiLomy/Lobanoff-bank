package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.data.entities.Account;
import ru.kpfu.itis.lobanov.dtos.TransactionDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.dtos.forms.*;

import java.math.BigDecimal;
import java.util.List;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface TransactionService {
    TransactionDto getById(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long transactionId
    );

    List<TransactionDto> getAllOperationsFromUser(
            @NotNull(message = USER_NOT_NULL)
            UserDto userDto
    );

    List<TransactionDto> getAllRecentTransactions(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long accountId
    );

    List<TransactionDto> getAllTransactionsFromUserExpenses(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    List<TransactionDto> getAllTransactionsFromUserReceipts(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    TransactionDto transferByPhone(
            @NotNull(message = PHONE_TRANSFER_FORM_NOT_NULL)
            PhoneTransferForm phoneTransferForm
    );

    TransactionDto transferBetweenAccounts(
            @NotNull(message = BETWEEN_ACCOUNTS_TRANSFER_FORM_NOT_NULL)
            BetweenAccountsTransferForm betweenAccountsTransferForm
    );

    TransactionDto transferByCard(
            @NotNull(message = CARD_TRANSFER_FORM_NOT_NULL)
            CardTransferForm cardTransferForm
    );

    TransactionDto transferByAccountDetails(
            @NotNull
            AccountDetailsTransferForm accountDetailsTransferForm
    );

    TransactionDto replenishByCard(
            @NotNull
            CardReplenishForm cardReplenishForm
    );

    void chargeInterestTransaction(
            @NotNull(message = ACCOUNT_NOT_NULL)
            Account account,
            @NotNull(message = AMOUNT_NOT_NULL)
            @PositiveOrZero(message = AMOUNT_POSITIVE_OR_ZERO)
            BigDecimal amount
    );
}
