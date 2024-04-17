package ru.kpfu.itis.lobanov.controllers;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.lobanov.configs.SecurityConfig;
import ru.kpfu.itis.lobanov.configs.TestConfig;
import ru.kpfu.itis.lobanov.controllers.accounts.UserProfileController;
import ru.kpfu.itis.lobanov.data.services.BankAccountService;
import ru.kpfu.itis.lobanov.data.services.OperationService;
import ru.kpfu.itis.lobanov.data.services.UserService;
import ru.kpfu.itis.lobanov.data.services.impl.UserDetailsServiceImpl;
import ru.kpfu.itis.lobanov.dtos.BankAccountDto;
import ru.kpfu.itis.lobanov.dtos.CardDto;
import ru.kpfu.itis.lobanov.dtos.Role;
import ru.kpfu.itis.lobanov.dtos.UserDto;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static ru.kpfu.itis.lobanov.utils.Constants.*;

@WebMvcTest(UserProfileController.class)
@Import({TestConfig.class, SecurityConfig.class, UserDetailsServiceImpl.class, DataSourceAutoConfiguration.class})
@ExtendWith(SpringExtension.class)
@DisplayName("UserProfileController API test")
public class UserProfileControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private BankAccountService bankAccountService;
    @MockBean
    private OperationService operationService;
    @MockBean
    private UserService userService;

    private static UserDto accountOwner;
    private static List<CardDto> userCards;
    private static BankAccountDto bankAccount;
    private static List<BankAccountDto> accounts;

    @BeforeAll
    public static void setUpData() {
        accountOwner = UserDto.builder()
                .id(USER_ID)
                .email(USER_EMAIL)
                .phone(USER_PHONE)
                .role(Role.USER)
                .isDeleted(false)
                .build();
        CardDto card = CardDto.builder()
                .owner(accountOwner)
                .number(CARD_NUMBER)
                .build();
        userCards = Collections.singletonList(card);
        bankAccount = BankAccountDto.builder()
                .id(BANK_ACCOUNT_ID)
                .name(BANK_ACCOUNT_NAME)
                .owner(accountOwner)
                .cards(userCards)
                .deposit(BANK_ACCOUNT_DEPOSIT)
                .beginningMonthDeposit(BANK_ACCOUNT_BEGINNING_MONTH_DEPOSIT)
                .operations(null)
                .build();
        accounts = Collections.singletonList(bankAccount);
    }

    @BeforeEach
    public void setUpServices() {
        given(bankAccountService.getAllUserAccounts(accountOwner)).willReturn(accounts);
        given(operationService.findAllByUserLimitRecent(bankAccount)).willReturn(null);
        given(userService.getCurrentUser()).willReturn(accountOwner);
    }

    @Nested
    @DisplayName("Render profile page view")
    class ShowProfilePage {
        @Nested
        @WithMockUser
        @DisplayName("When user is logged in")
        class LoggedInUser {
            public static final int BANK_ACCOUNT_SERVICE_INVOCATION_COUNT = 1;
            public static final int OPERATION_SERVICE_INVOCATION_COUNT = 1;
            public static final int USER_SERVICE_INVOCATION_COUNT = 1;

            @Test
            @DisplayName("Should return the HTTP status code 200")
            public void shouldReturnHttpStatusOk() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()))
                        .andExpect(status().isOk());
            }

            @Test
            @DisplayName("Should return profile view")
            public void shouldReturnProfileView() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()))
                        .andExpect(view().name(PROFILE_VIEW_NAME));
            }

            @Test
            @DisplayName("Should contain information about current user")
            public void shouldContainCurrentUser() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()))
                        .andExpect(model().attribute(CURRENT_USER_KEY,
                                allOf(
                                        hasProperty(ID_KEY, is(USER_ID)),
                                        hasProperty(EMAIL_KEY, is(USER_EMAIL)),
                                        hasProperty(PHONE_KEY, is(USER_PHONE)),
                                        hasProperty(ROLE_KEY, is(Role.USER))
                                )
                        ));
            }

            @Test
            @DisplayName("Should contain information about transactions")
            public void shouldContainTransactions() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()))
                        .andExpect(model().attribute(TRANSACTIONS_KEY, is(nullValue())));
            }

            @Test
            @DisplayName("Should contain information about certain number of bank accounts")
            public void shouldContainNumberOfAccounts() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()))
                        .andExpect(model().attribute(ACCOUNTS_KEY, hasSize(accounts.size())));
            }

            @Test
            @DisplayName("Should contain information about bank accounts")
            public void shouldContainAccounts() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()))
                        .andExpect(model().attribute(ACCOUNTS_KEY, hasItem(
                                allOf(
                                        hasProperty(ID_KEY, is(BANK_ACCOUNT_ID)),
                                        hasProperty(NAME_KEY, is(BANK_ACCOUNT_NAME)),
                                        hasProperty(OWNER_KEY, is(accountOwner)),
                                        hasProperty(CARDS_KEY, is(userCards)),
                                        hasProperty(DEPOSIT_KEY, is(BANK_ACCOUNT_DEPOSIT))
                                )
                        )));
            }

            @Test
            @DisplayName("Should have certain number of services invocations")
            public void shouldHaveServicesInvocations() throws Exception {
                mockMvc.perform(get(PROFILE_URL).with(csrf()));

                verify(bankAccountService, times(BANK_ACCOUNT_SERVICE_INVOCATION_COUNT)).getAllUserAccounts(accountOwner);
                verify(operationService, times(OPERATION_SERVICE_INVOCATION_COUNT)).findAllByUserLimitRecent(bankAccount);
                verify(userService, times(USER_SERVICE_INVOCATION_COUNT)).getCurrentUser();

                verifyNoMoreInteractions(bankAccountService);
                verifyNoMoreInteractions(operationService);
                verifyNoMoreInteractions(userService);
            }
        }

        @Nested
        @WithAnonymousUser
        @DisplayName("When user is anonymous")
        class NotLoggedInUser {
            @Test
            @DisplayName("Should return the HTTP status code 302")
            public void shouldReturnHttpStatusMovedTemporary() throws Exception {
                mockMvc.perform(get(PROFILE_URL))
                        .andExpect(status().isFound());
            }
        }
    }
}
