package ru.kpfu.itis.lobanov.controllers.transfers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.CardReplenishApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardReplenishForm;
import ru.kpfu.itis.lobanov.dtos.CardTransferForm;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CardReplenishController implements CardReplenishApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    @Override
    public String getReplenishPage(String accountId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(user);
        model.addAttribute("currentUser", user);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("accountsList", accounts);
        return "card_replenish";
    }

    @Override
    public String replenishByCreditCardNumber(CardReplenishForm cardReplenishForm, Model model) {
        operationService.replenishByCard(cardReplenishForm);
        return "redirect:/profile";
    }
}
