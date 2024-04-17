package ru.kpfu.itis.lobanov.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.CardTransferApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardTransferForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CardTransferController implements CardTransferApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
//    private final CardService cardService;
    private final UserService userService;

    @Override
    public String getTransfersPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("accountsList", accounts);
        return "transactions/card_transfer";
    }

    @Override
    public String transferByCreditCardNumber(CardTransferForm cardTransferForm, Model model) {
        operationService.transferByCard(cardTransferForm);
        return "redirect:/profile";
    }
}
