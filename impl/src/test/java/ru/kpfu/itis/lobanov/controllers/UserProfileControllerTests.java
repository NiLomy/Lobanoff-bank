package ru.kpfu.itis.lobanov.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.kpfu.itis.lobanov.configs.TestConfig;
import ru.kpfu.itis.lobanov.controllers.accounts.UserProfileController;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserProfileController.class)
@Import(TestConfig.class)
public class UserProfileControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void showNotAuthenticatedProfilePageTest() throws Exception {
        mockMvc.perform(get("/profile"))
                .andExpect(status().is4xxClientError());
    }
}
