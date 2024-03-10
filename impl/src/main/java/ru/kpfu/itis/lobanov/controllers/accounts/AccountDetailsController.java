package ru.kpfu.itis.lobanov.controllers.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.api.accounts.AccountDetailsApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;

import java.util.List;

@Controller
public class AccountDetailsController implements AccountDetailsApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    @Autowired
    public AccountDetailsController(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @Override
    public String showAccountDetailsPage(String accountId, Model model, Authentication authentication) {
        if (authentication != null) { // check if user is authorized
            User user = (User) authentication.getPrincipal();
            BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
            List<OperationDto> operations = operationService.findAllByUserLimitRecent(currentAccount);
            model.addAttribute("currentUser", user);
            model.addAttribute("currentAccount", currentAccount);
            model.addAttribute("transactions", operations);
            return "account";
        } else {
            return "redirect:/login";
        }
    }

    @Override
    public String updateAccountName(String accountId, String name, Model model) {
        BankAccountDto currentAccount = bankAccountService.updateAccountName(Long.parseLong(accountId), name);
        model.addAttribute("currentAccount", currentAccount);
        return "redirect:/account/" + accountId;
    }
}
