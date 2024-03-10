package ru.kpfu.itis.lobanov.api.transfers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CardTransferForm;

@RequestMapping("/transfers")
public interface CardTransferApi {
    @GetMapping("/card")
    String getTransfersPage(@RequestParam("accountId") String accountId, Model model, Authentication authentication);

    @PostMapping("/card")
    String transferByCreditCardNumber(@Valid @RequestBody CardTransferForm cardTransferForm, Model model);
}
