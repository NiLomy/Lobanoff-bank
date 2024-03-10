package ru.kpfu.itis.lobanov.configs;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.mappers.UserMapper;
import ru.kpfu.itis.lobanov.data.mappers.impl.UserMapperImpl;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.impl.RegistrationServiceImpl;
import ru.kpfu.itis.lobanov.data.services.impl.UserServiceImpl;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.State;

import java.util.ArrayList;
import java.util.List;

@TestConfiguration
public class TestConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserRepository userRepository() {
        List<User> users = new ArrayList<>();
        users.add(
                User.builder()
                        .id(1L)
                        .email("test1@mail.com")
                        .password("testPassword")
                        .role(Role.USER)
                        .state(State.ACTIVE)
                        .isDeleted(false)
                        .build()
        );
        users.add(
                User.builder()
                        .id(2L)
                        .email("test2@mail.com")
                        .password("testPassword")
                        .role(Role.USER)
                        .state(State.ACTIVE)
                        .isDeleted(false)
                        .build()
        );
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAllByIsDeletedIsFalse()).thenReturn(users);
        Mockito.when(userRepository.updateStateById(3L, State.BANNED.name())).thenReturn(
                User.builder()
                        .id(3L)
                        .email("test3@mail.com")
                        .password("testPassword")
                        .role(Role.USER)
                        .state(State.BANNED)
                        .isDeleted(false)
                        .build()
        );
        return userRepository;
    };

    @Bean
    public UserMapper userMapper() {
        return new UserMapperImpl();
    }

    @Bean
    public UserServiceImpl userService() {
        return new UserServiceImpl(userRepository(), userMapper());
    }

    @Bean
    public RegistrationServiceImpl registrationService() {
        return new RegistrationServiceImpl(userRepository(), passwordEncoder());
    }
}
