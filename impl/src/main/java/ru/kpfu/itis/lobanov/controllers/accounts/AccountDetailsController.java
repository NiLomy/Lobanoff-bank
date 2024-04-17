package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.accounts.AccountDetailsApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountDetailsController implements AccountDetailsApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final UserService userService;

    @Override
    public String showAccountDetailsPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        List<OperationDto> operations = operationService.findAllByUserLimitRecent(currentAccount);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("transactions", operations);
        return "accounts/account";
    }

    @Override
    public String updateAccountName(String accountId, String name, Model model) {
        BankAccountDto currentAccount = bankAccountService.updateAccountName(Long.parseLong(accountId), name);
        model.addAttribute("currentAccount", currentAccount);
        return "redirect:/account/" + accountId;
    }
}
