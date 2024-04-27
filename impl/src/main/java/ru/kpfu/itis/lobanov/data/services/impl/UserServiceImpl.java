package ru.kpfu.itis.lobanov.data.services.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;
import ru.kpfu.itis.lobanov.dtos.UserDto;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.UserMapper;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.UserService;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    @Override
    public UserDto getById(Long userId) {
        return userMapper.toResponse(userRepository.findById(userId).orElse(null));
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toListResponse(userRepository.findAllByIsDeletedIsFalse());
    }

    @Override
    public UserDto getUserByPhone(String phone) {
        return userMapper.toResponse(userRepository.findByPhone(phone));
    }

    @Override
    public UserDto getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS")))
            return null;
        User user = (User) authentication.getPrincipal();
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .isDeleted(user.getIsDeleted())
                .build();
    }

    @Override
    public UserDto register(RegistrationForm registrationForm) {
        if (registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            User user = User.builder()
                    .email(registrationForm.getEmail())
                    .password(passwordEncoder.encode(registrationForm.getPassword()))
                    .role(Role.USER)
                    .state(State.ACTIVE)
                    .isDeleted(false)
                    .build();
            return userMapper.toResponse(userRepository.save(user));
        }
        return null;
    }

    @Override
    public UserDto banUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        if (!user.getRole().equals(Role.ADMIN)) {
            log.info("User {} was banned.", user);
            return userMapper.toResponse(userRepository.updateStateById(userId, State.BANNED.toString()));
        } else {
            log.warn("You can not ban {} because of his role.", user);
            return null;
        }
    }

    // soft-delete
    @Override
    public UserDto deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(IllegalArgumentException::new);
        user.setIsDeleted(true);
        log.info("User {} was deleted.", user);
        return userMapper.toResponse(userRepository.save(user));
    }
}
