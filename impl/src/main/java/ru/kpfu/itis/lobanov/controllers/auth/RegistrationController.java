package ru.kpfu.itis.lobanov.controllers.auth;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.lobanov.api.auth.RegistrationApi;
import ru.kpfu.itis.lobanov.dtos.RegistrationForm;
import ru.kpfu.itis.lobanov.data.services.RegistrationService;

@Controller
public class RegistrationController implements RegistrationApi {
    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @Override
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "registration";
    }

    @Override
    public String register(@Valid @ModelAttribute("registrationForm") @RequestBody RegistrationForm registrationForm, Errors errors) {
        if (errors.hasErrors()) {
            return "registration";
        }

        registrationService.register(registrationForm);
        return "redirect:/login";
    }
}
