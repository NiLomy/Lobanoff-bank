package ru.kpfu.itis.lobanov.api.accounts;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@RequestMapping("/profile")
public interface UserProfileApi {
    @GetMapping
    String showProfilePage(Model model, Authentication authentication);
}
