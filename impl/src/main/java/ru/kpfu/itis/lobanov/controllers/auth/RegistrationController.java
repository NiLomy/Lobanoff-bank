package ru.kpfu.itis.lobanov.controllers.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import ru.kpfu.itis.lobanov.api.auth.RegistrationApi;
import ru.kpfu.itis.lobanov.data.services.RegistrationService;
import ru.kpfu.itis.lobanov.dtos.RegistrationForm;

@Controller
@RequiredArgsConstructor
public class RegistrationController implements RegistrationApi {
    private final RegistrationService registrationService;

    @Override
    public String showRegistrationPage(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        return "auth/registration";
    }

    @Override
    public String register(@Valid @ModelAttribute("registrationForm") @RequestBody RegistrationForm registrationForm, Errors errors) {
        if (errors.hasErrors()) {
            return "auth/registration";
        }

        registrationService.register(registrationForm);
        return "redirect:/login";
    }
}
