package ru.kpfu.itis.lobanov.controllers.cards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.api.cards.CreatingCardApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.CardService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@Controller
public class CreatingCardController implements CreatingCardApi {
    private final BankAccountService bankAccountService;
    private final CardService cardService;

    @Autowired
    public CreatingCardController(BankAccountService bankAccountService, CardService cardService) {
        this.bankAccountService = bankAccountService;
        this.cardService = cardService;
    }

    @Override
    public String getCreatingCardPage(String accountId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("currentUser", user);
        return "creating_card";
    }

    @Override
    public String createCard(String accountId, Model model) {
        CardDto cardDto = cardService.create(Long.parseLong(accountId));
        BankAccountDto currentAccount = bankAccountService.bindCard(Long.parseLong(accountId), cardDto);
//        model.addAttribute("currentAccount", currentAccount);
        return "redirect:/account/" + accountId;
    }
}
