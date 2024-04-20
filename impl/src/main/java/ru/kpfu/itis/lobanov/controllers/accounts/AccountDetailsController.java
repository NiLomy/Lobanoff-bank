package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;
import ru.kpfu.itis.lobanov.api.accounts.AccountDetailsApi;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.DateService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;

import java.util.List;

import static ru.kpfu.itis.lobanov.utils.NamingConstants.*;

@Controller
@SessionAttributes(CURRENT_USER_KEY)
@RequiredArgsConstructor
public class AccountDetailsController implements AccountDetailsApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final DateService dateService;

    @Override
    public String showAccountDetailsPage(String accountId, Model model) {
        BankAccountDto currentAccount = bankAccountService.getAccountById(Long.parseLong(accountId));
        List<OperationDto> operations = operationService.findAllByUserLimitRecent(currentAccount);
        String currentDate = dateService.getCurrentDate();

        model.addAttribute(CURRENT_ACCOUNT_KEY, currentAccount);
        model.addAttribute(TRANSACTIONS_KEY, operations);
        model.addAttribute(CURRENT_DATE_KEY, currentDate);
        return "accounts/account";
    }

    @Override
    public String updateAccountName(String accountId, String name, Model model) {
        BankAccountDto currentAccount = bankAccountService.updateAccountName(Long.parseLong(accountId), name);
        model.addAttribute(CURRENT_ACCOUNT_KEY, currentAccount);
        return "redirect:/account/" + accountId;
    }
}
