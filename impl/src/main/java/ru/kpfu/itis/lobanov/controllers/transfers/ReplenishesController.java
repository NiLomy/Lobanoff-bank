package ru.kpfu.itis.lobanov.controllers.transfers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.ReplenishesApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;

@Controller
@RequiredArgsConstructor
public class ReplenishesController implements ReplenishesApi {
    private final BankAccountService bankAccountService;

    @Override
    public String getReplenishPage(String accountId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        model.addAttribute("currentUser", user);
        model.addAttribute("currentAccount", currentAccount);
        return "replenishes";
    }
}
