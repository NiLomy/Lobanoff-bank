package ru.kpfu.itis.lobanov.api.transfers;

import org.springframework.security.core.Authentication;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/replenishes")
public interface ReplenishesApi {
    @GetMapping("/{id}")
    String getReplenishPage(@PathVariable("id") String accountId, Model model, Authentication authentication);
}
