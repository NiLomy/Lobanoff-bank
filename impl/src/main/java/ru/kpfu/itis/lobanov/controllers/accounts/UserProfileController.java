package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.accounts.UserProfileApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserProfileController implements UserProfileApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;
    private final UserService userService;

    @Override
    public String showProfilePage(Model model) {
        UserDto currentUser = userService.getCurrentUser();
        List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(currentUser);
//            List<BankAccountDto> cardAccounts = bankAccountService.getAllUserCardAccounts(UserDto.fromUser(user));
        List<OperationDto> operations = operationService.findAllByUserLimitRecent(accounts.stream().filter(account -> account.getCards() != null).toList().get(0));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("accounts", accounts);
        model.addAttribute("transactions", operations);
        return "accounts/profile";
    }
}
