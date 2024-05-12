package ru.kpfu.itis.lobanov.data.services;

import jakarta.validation.constraints.*;
import org.springframework.validation.annotation.Validated;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.requests.BindCardRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CloseAccountRequest;
import ru.kpfu.itis.lobanov.dtos.requests.CreateAccountRequest;

import java.util.List;

import static ru.kpfu.itis.lobanov.utils.ValidationMessages.*;

@Validated
public interface AccountService {
    List<BankAccountDto> getAllAccounts();

    List<BankAccountDto> getAllUserAccounts(Long userId);

    List<BankAccountDto> getAllUserCardAccounts(Long userId);

    BankAccountDto createAccount(CreateAccountRequest request);

    void closeAccount(CloseAccountRequest request);

    BankAccountDto getAccountById(Long id);

    BankAccountDto getUserMainAccount(Long userId);

    BankAccountDto updateAccountName(Long id, String name);

    BankAccountDto bindCard(BindCardRequest request);

    AccountStatementDto getStatement(
            @NotNull(message = ID_NOT_NULL)
            @PositiveOrZero(message = ID_POSITIVE_OR_ZERO)
            Long accountId,
            @NotNull(message = DATE_NOT_NULL)
            @NotBlank(message = DATE_NOT_BLANK)
            @Size(min = 4, max = 10, message = DATE_SIZE)
            String date
    );

    void chargeInterestOnAccounts();
}
