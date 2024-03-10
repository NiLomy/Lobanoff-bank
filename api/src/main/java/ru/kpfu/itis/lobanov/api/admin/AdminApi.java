package ru.kpfu.itis.lobanov.api.admin;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kpfu.itis.lobanov.dtos.UserDto;

@RequestMapping("/admin")
public interface AdminApi {
    @GetMapping
    String showAllUsers(Model model);

    @PostMapping
    String banUser(UserDto userDto);
}
