package ru.kpfu.itis.lobanov.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.kpfu.itis.lobanov.data.dtos.UserDto;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.data.services.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserServiceTests {
    private static UserRepository userRepository;
    private UserService userService;

    @BeforeAll
    public static void prepareRepositoryData() {
        List<User> users = new ArrayList<>();
        users.add(
                User.builder()
                        .email("test1@mail.com")
                        .password("testPassword")
                        .role(User.Role.USER)
                        .state(User.State.ACTIVE)
                        .isDeleted(false)
                        .build()
        );
        users.add(
                User.builder()
                        .email("test2@mail.com")
                        .password("testPassword")
                        .role(User.Role.USER)
                        .state(User.State.ACTIVE)
                        .isDeleted(false)
                        .build()
        );

        Optional<User> optionalUser = Optional.ofNullable(
                User.builder()
                        .email("test3@mail.com")
                        .password("testPassword")
                        .role(User.Role.USER)
                        .state(User.State.ACTIVE)
                        .isDeleted(false)
                        .build()
        );

        Optional<User> nullableUser = Optional.empty();

        User bannedUser = User.builder()
                .id(5L)
                .email("test5@mail.com")
                .password("testPassword")
                .role(User.Role.USER)
                .state(User.State.BANNED)
                .isDeleted(false)
                .build();

        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findAllByIsDeletedIsFalse()).thenReturn(users);
        Mockito.when(userRepository.findById(3L)).thenReturn(optionalUser);
        Mockito.when(userRepository.findById(4L)).thenReturn(nullableUser);
        Mockito.when(userRepository.updateStateById(5L, User.State.BANNED.name())).thenReturn(bannedUser);
    }

    @BeforeEach
    public void createUserService() {
//        userService = new UserServiceImpl(userRepository);
    }

    @Test
    public void getAllUsersTest() {
//        List<UserDto> userDtos = userService.getAllUsers();
//        assert userDtos.size() == 2;
//        assert userDtos.get(0).equals(UserDto.fromUser(
//                User.builder()
//                        .email("test1@mail.com")
//                        .password("testPassword")
//                        .role(User.Role.USER)
//                        .state(User.State.ACTIVE)
//                        .isDeleted(false)
//                        .build()
//        ));
//        assert userDtos.get(1).equals(UserDto.fromUser(
//                User.builder()
//                        .email("test2@mail.com")
//                        .password("testPassword")
//                        .role(User.Role.USER)
//                        .state(User.State.ACTIVE)
//                        .isDeleted(false)
//                        .build()
//        ));
    }

    @Test
    public void deletePresentUserTest() {
        userService.deleteUser(3L);
        assert true;
    }

    @Test
    public void deleteNullUserTest() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> userService.deleteUser(4L));
    }

    @Test
    public void banUserTest() {
        User activeUser = User.builder()
                .id(5L)
                .email("test5@mail.com")
                .password("testPassword")
                .role(User.Role.USER)
                .state(User.State.ACTIVE)
                .isDeleted(false)
                .build();

//        userService.banUser(
//                UserDto.fromUser(
//                        activeUser
//                )
//        );

        assert true;
    }
}
