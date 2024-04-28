package ru.kpfu.itis.lobanov.configs;

import org.mockito.Mockito;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.lobanov.data.entities.BankAccount;
import ru.kpfu.itis.lobanov.data.entities.Card;
import ru.kpfu.itis.lobanov.data.entities.User;
import ru.kpfu.itis.lobanov.data.repositories.BankAccountRepository;
import ru.kpfu.itis.lobanov.data.repositories.UserRepository;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.TransactionService;
import ru.kpfu.itis.lobanov.data.services.impl.BankAccountServiceImpl;
import ru.kpfu.itis.lobanov.data.services.impl.TransactionServiceImpl;
import ru.kpfu.itis.lobanov.dtos.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    public BankAccountRepository bankAccountRepository() {
        User owner = User.builder()
                .id(1L)
                .email("test@mail.com")
                .password("testPassword")
                .role(Role.USER)
                .state(State.ACTIVE)
                .isDeleted(false)
                .build();
        Card card = Card.builder()
                .owner(owner)
                .cvv("302")
                .expiration("03/28")
                .number("1144485810352102")
                .build();

        BankAccount bankAccount = BankAccount.builder()
                .name("AAAA")
                .owner(owner)
                .cards(List.of(card))
                .deposit(1000L)
                .operations(null)
                .number("1")
                .build();
        BankAccountRepository bankAccountRepository = Mockito.mock(BankAccountRepository.class);
        Mockito.when(bankAccountRepository.findById(1L)).thenReturn(Optional.of(bankAccount));
        return bankAccountRepository;
    }


    @Bean
    public BankAccountService bankAccountService() {
        return Mockito.mock(BankAccountServiceImpl.class);
//        return new BankAccountServiceImpl(bankAccountRepository(), userRepository(), null, bankAccountMapper(), null);
    }

    @Bean
    TransactionService operationService() {
        return Mockito.mock(TransactionServiceImpl.class);
    }
}
