package ru.kpfu.itis.lobanov.controllers.transfers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.BetweenAccountsTransferApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.BetweenAccountsTransferForm;

import java.util.List;
import java.util.Objects;

@Controller
public class BetweenAccountsTransferController implements BetweenAccountsTransferApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    @Autowired
    public BetweenAccountsTransferController(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @Override
    public String getTransfersPage(String accountId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        BankAccountDto anotherAccount = bankAccountService.getAllUserAccounts(user).stream().filter(account -> !Objects.equals(account.getId(), currentAccount.getId())).findAny().orElseThrow(IllegalArgumentException::new);
        List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(user);
        model.addAttribute("currentUser", user);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("anotherAccount", anotherAccount);
        model.addAttribute("accountsList", accounts);
        return "between_accounts_transfer";
    }

    @Override
    public String transferBetweenAccounts(BetweenAccountsTransferForm betweenAccountsTransferForm, Model model) {
        operationService.transferBetweenAccounts(betweenAccountsTransferForm);
        return "redirect:/profile";
    }
}
