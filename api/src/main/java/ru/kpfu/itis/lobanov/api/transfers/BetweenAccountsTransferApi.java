package ru.kpfu.itis.lobanov.api.transfers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.BetweenAccountsTransferForm;

@RequestMapping("/transfers")
public interface BetweenAccountsTransferApi {
    @GetMapping("/between-accounts")
    String getTransfersPage(@RequestParam("accountId") String accountId, Model model, Authentication authentication);

    @PostMapping("/between-accounts")
    String transferBetweenAccounts(@Valid @RequestBody BetweenAccountsTransferForm betweenAccountsTransferForm, Model model);
}
