package ru.kpfu.itis.lobanov.controllers.accounts;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import ru.kpfu.itis.lobanov.api.accounts.UserProfileApi;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.OperationDto;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.List;

@Controller
@Slf4j
public class UserProfileController implements UserProfileApi {
    private final BankAccountService bankAccountService;
    private final OperationService operationService;

    @Autowired
    public UserProfileController(BankAccountService bankAccountService, OperationService operationService) {
        this.bankAccountService = bankAccountService;
        this.operationService = operationService;
    }

    @Override
    public String showProfilePage( Model model, Authentication authentication) {
        if (authentication != null) { // check if user is authorized
            User user = (User) authentication.getPrincipal();
            List<BankAccountDto> accounts = bankAccountService.getAllUserAccounts(user);
//            List<BankAccountDto> cardAccounts = bankAccountService.getAllUserCardAccounts(UserDto.fromUser(user));
            List<OperationDto> operations = operationService.findAllByUserLimitRecent(accounts.stream().filter(account -> account.getCards() != null).toList().get(0));
            model.addAttribute("currentUser", user);
            model.addAttribute("accounts", accounts);
            model.addAttribute("transactions", operations);
            return "profile";
        } else {
            return "redirect:/login";
        }
    }
}
