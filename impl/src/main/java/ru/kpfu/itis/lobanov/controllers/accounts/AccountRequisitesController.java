package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.accounts.AccountRequisitesApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.RequisitesService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;
import ru.kpfu.itis.lobanov.dtos.RequisitesDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class AccountRequisitesController implements AccountRequisitesApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final RequisitesService requisitesService;
    private final UserService userService;

    @Override
    public String getAccountRequisitesPage(String accountId, Model model) {
        UserDto currentUser = userService.getCurrentUser();
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        RequisitesDto requisitesDto = requisitesService.getRequisites(currentAccount.getId());
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentAccount", currentAccount);
        model.addAttribute("requisites", requisitesDto);
        return "accounts/account_requisites";
    }
}
