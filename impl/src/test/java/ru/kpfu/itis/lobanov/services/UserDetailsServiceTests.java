package ru.kpfu.itis.lobanov.services;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.impl.UserDetailsServiceImpl;

public class UserDetailsServiceTests {
    private static UserRepository userRepository;
    private UserDetailsServiceImpl userDetailsService;

    @BeforeAll
    public static void prepareRepositoryData() {
        User user = User.builder()
                .id(1L)
                .email("test1@mail.com")
                .password("testPassword")
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .isDeleted(false)
                .build();

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findByEmail("test1@mail.com")).thenReturn(user);
    }

    @BeforeEach
    public void createUserService() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }

    @Test
    public void loadUserByUsernameTest() {
        User user = User.builder()
                .id(1L)
                .email("test1@mail.com")
                .password("testPassword")
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .isDeleted(false)
                .build();
        assert userDetailsService.loadUserByUsername("test1@mail.com").equals(user);
    }
}
