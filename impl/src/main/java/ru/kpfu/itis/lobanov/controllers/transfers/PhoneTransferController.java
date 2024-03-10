package ru.kpfu.itis.lobanov.controllers.transfers;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.PhoneTransferApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;

@Controller
public class PhoneTransferController implements PhoneTransferApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    @Autowired
    public PhoneTransferController(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @Override
    public String getTransfersPage(String accountId, Model model, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        model.addAttribute("currentUser", user);
        model.addAttribute("currentAccount", currentAccount);
        return "phone_transfer";
    }

    @Override
    public String transferByPhone(String accountId, ru.kpfu.itis.lobanov.dtos.PhoneTransferForm phoneTransferForm, Model model) {
        operationService.transferByPhone(Long.parseLong(accountId), phoneTransferForm);
        return "redirect:/profile";
    }
}
