package ru.kpfu.itis.lobanov.controllers.transfers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.TransfersApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;

@Controller
public class TransfersController implements TransfersApi {
    private final BankAccountService bankAccountService;

    @Autowired
    public TransfersController(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }

    @Override
    public String getTransfersPage(String accountId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        model.addAttribute("currentUser", user);
        model.addAttribute("currentAccount", currentAccount);
        return "transfers";
    }
}
