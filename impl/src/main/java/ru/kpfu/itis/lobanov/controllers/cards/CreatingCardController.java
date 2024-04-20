package ru.kpfu.itis.lobanov.controllers.cards;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.cards.CreatingCardApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.CardService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.CURRENT_ACCOUNT_KEY;
import static ru.kpfu.itis.lobanov.utils.NamingConstants.CURRENT_USER_KEY;

@Controller
@RequiredArgsConstructor
public class CreatingCardController implements CreatingCardApi {
    private final BankAccountService bankAccountService;
    private final CardService cardService;
    private final UserService userService;

    @Override
    public String getCreatingCardPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));

        model.addAttribute(CURRENT_ACCOUNT_KEY, currentAccount);
        model.addAttribute(CURRENT_USER_KEY, currentUser);

        return "cards/creating_card";
    }

    @Override
    public String createCard(String accountId, Model model) {
        CardDto cardDto = cardService.create(Long.parseLong(accountId));
        BankAccountDto currentAccount = bankAccountService.bindCard(Long.parseLong(accountId), cardDto);
//        model.addAttribute("currentAccount", currentAccount);
        return "redirect:/account/" + accountId;
    }
}
