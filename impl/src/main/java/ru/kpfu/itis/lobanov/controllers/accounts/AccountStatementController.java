package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.kpfu.itis.lobanov.api.accounts.AccountStatementApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.DateService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.time.LocalDate;
import java.util.List;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.*;

@Controller
@SessionAttributes(CURRENT_USER_KEY)
@RequiredArgsConstructor
public class AccountStatementController implements AccountStatementApi {
    private final BankAccountService bankAccountService;
    private final DateService dateService;

    @Override
    public String getAccountStatementPage(String accountId, String date, Model model) {
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        AccountStatementDto accountStatementDto = bankAccountService.getStatement(Long.parseLong(accountId), date);
        String dateFrom = dateService.formatDate(dateService.getFullDate(date));
        String dateTo = dateService.formatDate(dateService.getNextMonth(date));

        model.addAttribute(CURRENT_ACCOUNT_KEY, currentAccount);
        model.addAttribute(STATEMENT_KEY, accountStatementDto);
        model.addAttribute(DATE_FROM_KEY, dateFrom);
        model.addAttribute(DATE_TO_KEY, dateTo);

        return "accounts/account_statement";
    }
}
