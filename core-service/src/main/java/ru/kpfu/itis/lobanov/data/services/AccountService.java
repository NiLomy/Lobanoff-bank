package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;

import java.util.List;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.MAX_DATE_SIZE;
import static ru.kpfu.itis.lobanov.utils.ValueConstants.MIN_DATE_SIZE;

@Validated
public interface AccountService {
    List<BankAccountDto> getAllAccounts();

    List<BankAccountDto> getAllUserAccounts(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    List<BankAccountDto> getAllUserCardAccounts(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    BankAccountDto createAccount(
            @NotNull(message = CREATE_ACCOUNT_REQUEST_NOT_NULL)
            CreateAccountRequest request
    );

    void closeAccount(
            @NotNull(message = CLOSE_ACCOUNT_REQUEST_NOT_NULL)
            CloseAccountRequest request
    );

    BankAccountDto getAccountById(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long id
    );

    BankAccountDto getUserMainAccount(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long userId
    );

    BankAccountDto updateAccountName(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long id,
            @NotNull(message = NAME_NOT_NULL)
            @NotBlank(message = NAME_NOT_BLANK)
            String name
    );

    BankAccountDto bindCard(
            @NotNull(message = BIND_CARD_REQUEST_NOT_NULL)
            BindCardRequest request
    );

    AccountStatementDto getStatement(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long accountId,
            @NotNull(message = DATE_NOT_NULL)
            @NotBlank(message = DATE_NOT_BLANK)
            @Size(min = MIN_DATE_SIZE, max = MAX_DATE_SIZE, message = DATE_SIZE)
            String date
    );

    void chargeInterestOnAccounts();
}
