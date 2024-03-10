package ru.kpfu.itis.lobanov.api.transfers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@RequestMapping("/transfers")
public interface TransfersApi {
    @GetMapping("/{id}")
    String getTransfersPage(@PathVariable("id") String accountId, Model model, Authentication authentication);
}
