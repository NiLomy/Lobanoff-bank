package ru.kpfu.itis.lobanov.api.transfers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.AccountDetailsTransferForm;

@RequestMapping("/transfers")
public interface AccountDetailsTransferApi {
    @GetMapping("/account-details")
    String getTransfersPage(@RequestParam("accountId") String accountId, Model model, Authentication authentication);

    @PostMapping("/account-details")
    String transferByAccountDetails(@Valid @RequestBody AccountDetailsTransferForm accountDetailsTransferForm, Model model);
}
