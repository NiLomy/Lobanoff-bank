//package ru.kpfu.itis.lobanov.controllers;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.*;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import ru.kpfu.itis.lobanov.configs.SecurityConfig;
//import ru.kpfu.itis.lobanov.configs.TestConfig;
//import ru.kpfu.itis.lobanov.data.services.UserService;
//import ru.kpfu.itis.lobanov.data.services.impl.UserDetailsServiceImpl;
//import ru.kpfu.itis.lobanov.dtos.forms.RegistrationForm;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static ru.kpfu.itis.lobanov.utils.Constants.*;
//
//@WebMvcTest
//@Import({TestConfig.class, SecurityConfig.class, UserDetailsServiceImpl.class, DataSourceAutoConfiguration.class})
//@ExtendWith(SpringExtension.class)
//@DisplayName("RegistrationController API test")
//public class RegistrationControllerTests {
//    @Autowired
//    private MockMvc mockMvc;
//    @MockBean
//    private UserService userService;
//
//    private static RegistrationForm registrationForm;
//
//    @BeforeAll
//    public static void setUpData() {
//        registrationForm = new RegistrationForm(USER_EMAIL, USER_PASSWORD, USER_PASSWORD);
//    }
//
//    @BeforeEach
//    public void setUpServices() {
//        doNothing().when(userService).register(any(RegistrationForm.class));
//    }
//
//    @Nested
//    @DisplayName("Render registration page view")
//    class ShowRegistrationPage {
//        @Test
//        @DisplayName("Should return the HTTP status code 200")
//        public void shouldReturnHttpStatusOk() throws Exception {
//            mockMvc.perform(get(REGISTRATION_URL))
//                    .andExpect(status().isOk());
//        }
//
//        @Test
//        @DisplayName("Should return registration view")
//        public void shouldReturnProfileView() throws Exception {
//            mockMvc.perform(get(REGISTRATION_URL))
//                    .andExpect(view().name(REGISTRATION_VIEW_NAME));
//        }
//
//        @Test
//        @DisplayName("Should contain information about registration")
//        public void showRegistrationPageTest() throws Exception {
//            mockMvc.perform(get(REGISTRATION_URL))
//                    .andExpect(model().attribute(REGISTRATION_FORM_KEY, new RegistrationForm()));
//        }
//    }
//
//    @Nested
//    @DisplayName("Register user")
//    class Register {
//        @Nested
//        @DisplayName("With csrf")
//        class WithCsrf {
//            @Test
//            @DisplayName("Should return the HTTP status code 200")
//            public void shouldReturnHttpStatusOk() throws Exception {
//                mockMvc.perform(
//                                post(REGISTRATION_URL).with(csrf())
//                                        .content(asJsonString(registrationForm))
//                                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk());
//            }
//
//            @Test
//            @DisplayName("Should have certain number of services invocations")
//            public void shouldHaveServicesInvocations() throws Exception {
//                mockMvc.perform(
//                        post(REGISTRATION_URL).with(csrf())
//                                .content(asJsonString(registrationForm))
//                                .contentType(MediaType.APPLICATION_JSON));
//
//                verifyNoInteractions(userService);
//            }
//        }
//
//        @Nested
//        @DisplayName("Without csrf")
//        class WithoutCsrf {
//            @Test
//            @DisplayName("Should return the HTTP status code 403")
//            public void shouldReturnHttpStatusForbidden() throws Exception {
//                mockMvc.perform(
//                                post(REGISTRATION_URL)
//                                        .content(asJsonString(registrationForm))
//                                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isForbidden());
//            }
//
//            @Test
//            @DisplayName("Should have certain number of services invocations")
//            public void shouldHaveServicesInvocations() throws Exception {
//                mockMvc.perform(
//                        post(REGISTRATION_URL)
//                                .content(asJsonString(registrationForm))
//                                .contentType(MediaType.APPLICATION_JSON));
//
//                verifyNoInteractions(userService);
//            }
//        }
//    }
//
//    private static String asJsonString(final Object obj) {
//        try {
//            return new ObjectMapper().writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
