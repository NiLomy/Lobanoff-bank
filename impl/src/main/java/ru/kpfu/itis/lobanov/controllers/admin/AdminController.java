package ru.kpfu.itis.lobanov.controllers.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.kpfu.itis.lobanov.api.admin.AdminApi;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.data.services.UserService;

@Controller
@RequiredArgsConstructor
public class AdminController implements AdminApi {
    private final UserService userService;

    @GetMapping
    public String showAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users";
    }

    @Override
    public String banUser(UserDto userDto) {
        userService.banUser(userDto);
        return "redirect:/admin";
    }
}
