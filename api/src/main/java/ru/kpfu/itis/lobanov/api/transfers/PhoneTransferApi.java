package ru.kpfu.itis.lobanov.api.transfers;

import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.PhoneTransferForm;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@RequestMapping("/transfers")
public interface PhoneTransferApi {
    @GetMapping("/phone")
    String getTransfersPage(@RequestParam("accountId") String accountId, Model model, Authentication authentication);

    @PostMapping("/phone")
    String transferByPhone(@RequestParam("accountId") String accountId, @Valid @RequestBody PhoneTransferForm phoneTransferForm, Model model);
}
