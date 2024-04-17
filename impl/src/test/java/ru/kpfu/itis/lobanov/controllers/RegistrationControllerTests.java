package ru.kpfu.itis.lobanov.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.kpfu.itis.lobanov.configs.TestConfig;
import ru.kpfu.itis.lobanov.controllers.auth.RegistrationController;
import ru.kpfu.itis.lobanov.data.services.RegistrationService;
import ru.kpfu.itis.lobanov.dtos.RegistrationForm;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RegistrationController.class)
@Import(TestConfig.class)
public class RegistrationControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private RegistrationService registrationService;

    @Test
    @WithMockUser(username = "user")
    public void showRegistrationPageTest() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("auth/registration"))
                .andExpect(model().attribute("registrationForm", isA(RegistrationForm.class)))
                .andExpect(content().string(containsString("Registration")));
    }

    @Test
    @WithMockUser(username = "test1@mail.com")
    public void registerWithCsrfTest() throws Exception {
        RegistrationForm registrationForm = new RegistrationForm("test1@mail.com", "password", "password");
        mockMvc.perform(
                        post("/register").with(csrf())
                                .content(asJsonString(registrationForm))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verifyNoMoreInteractions(registrationService);
    }

    @Test
    @WithMockUser(username = "test1@mail.com")
    public void registerWithoutCsrfTest() throws Exception {
        RegistrationForm registrationForm = new RegistrationForm("test1@mail.com", "password", "password");

        mockMvc.perform(
                        post("/register")
                                .content(asJsonString(registrationForm))
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
        verifyNoMoreInteractions(registrationService);
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
