package ru.kpfu.itis.lobanov.api.auth;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.dtos.RegistrationForm;

@RequestMapping("/register")
public interface RegistrationApi {
    @GetMapping
    String showRegistrationPage(Model model);

    @PostMapping
    String register(@Valid @ModelAttribute("registrationForm") @RequestBody RegistrationForm registrationForm, Errors errors);
}
