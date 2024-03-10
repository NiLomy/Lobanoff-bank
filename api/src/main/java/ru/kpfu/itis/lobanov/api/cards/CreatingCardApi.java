package ru.kpfu.itis.lobanov.api.cards;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@Controller
@RequestMapping("/adding-card")
public interface CreatingCardApi {
    @GetMapping("/{id}")
    public String getCreatingCardPage(@PathVariable("id") String accountId, Model model, Authentication authentication);

    @PostMapping("/{id}")
    public String createCard(@PathVariable("id") String accountId, Model model);
}
