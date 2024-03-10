package ru.kpfu.itis.lobanov.api.transfers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.CardReplenishForm;
import ru.kpfu.itis.lobanov.dtos.CardTransferForm;

@RequestMapping("/replenishes")
public interface CardReplenishApi {
    @GetMapping("/card")
    String getReplenishPage(@RequestParam("accountId") String accountId, Model model, Authentication authentication);

    @PostMapping("/card")
    String replenishByCreditCardNumber(@Valid @RequestBody CardReplenishForm cardReplenishForm, Model model);
}
