package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.accounts.AccountStatementApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.AccountStatementDto;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountStatementController implements AccountStatementApi {
    private final BankAccountService bankAccountService;
    private final UserService userService;

    @Override
    public String getAccountStatementPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        AccountStatementDto accountStatementDto = bankAccountService.getStatement(Long.parseLong(accountId));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("statement", accountStatementDto);
        return "accounts/account_statement";
    }
}
