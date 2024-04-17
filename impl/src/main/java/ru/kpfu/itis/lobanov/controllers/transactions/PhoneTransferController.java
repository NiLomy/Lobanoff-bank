package ru.kpfu.itis.lobanov.controllers.transactions;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.transfers.PhoneTransferApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@Controller
@RequiredArgsConstructor
public class PhoneTransferController implements PhoneTransferApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final UserService userService;

    @Override
    public String getTransfersPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        return "transactions/phone_transfer";
    }

    @Override
    public String transferByPhone(String accountId, ru.kpfu.itis.lobanov.dtos.PhoneTransferForm phoneTransferForm, Model model) {
        operationService.transferByPhone(Long.parseLong(accountId), phoneTransferForm);
        return "redirect:/profile";
    }
}
