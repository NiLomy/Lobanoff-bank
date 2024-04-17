package ru.kpfu.itis.lobanov.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.BetweenAccountsTransferApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.BetweenAccountsTransferForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class BetweenAccountsTransferController implements BetweenAccountsTransferApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final UserService userService;

    @Override
    public String getTransfersPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        BankAccountDto anotherAccount = bankAccountService.getAllUserAccounts(currentUser).stream().filter(account -> !Objects.equals(account.getId(), currentAccount.getId())).findAny().orElseThrow(IllegalArgumentException::new);
        List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(currentUser);
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("anotherAccount", anotherAccount);
        model.addAttribute("accountsList", accounts);
        return "transactions/between_accounts_transfer";
    }

    @Override
    public String transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm, Model model) {
        operationService.transferBetweenAccounts(betweenAccountsTransferForm);
        return "redirect:/profile";
    }
}
