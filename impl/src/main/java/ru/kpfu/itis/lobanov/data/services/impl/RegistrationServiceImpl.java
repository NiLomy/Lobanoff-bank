package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.dtos.RegistrationForm;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.RegistrationService;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void register(RegistrationForm registrationForm) {
        if (registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            User user = User.builder()
                    .email(registrationForm.getEmail())
                    .password(passwordEncoder.encode(registrationForm.getPassword()))
                    .role(Role.USER)
                    .state(State.ACTIVE)
                    .isDeleted(false)
                    .build();
            userRepository.save(user);
            log.info("User {} was saved.", user);
        }
    }
}
