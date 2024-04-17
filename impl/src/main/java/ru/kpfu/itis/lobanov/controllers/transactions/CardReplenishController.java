package ru.kpfu.itis.lobanov.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.CardReplenishApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardReplenishForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CardReplenishController implements CardReplenishApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final UserService userService;

    @Override
    public String getReplenishPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("accountsList", accounts);
        return "transactions/card_replenish";
    }

    @Override
    public String replenishByCreditCardNumber(CardReplenishForm cardReplenishForm, Model model) {
        operationService.replenishByCard(cardReplenishForm);
        return "redirect:/profile";
    }
}
