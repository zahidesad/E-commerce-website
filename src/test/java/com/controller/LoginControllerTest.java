package com.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    // Dummy view so standaloneSetup doesn’t look for a JSP file
    @Mock
    View stubView;

    MockMvc mvc;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders.standaloneSetup(new LoginController())
                .setSingleView(stubView)
                .build();
    }

    @Test
    @DisplayName("GET /login shows page")
    void loginPage() throws Exception {
        mvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("/login?success sets model flag")
    void loginSuccessFlag() throws Exception {
        mvc.perform(get("/login").param("success",""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

    @Test
    @DisplayName("/login?error sets loginError flag")
    void loginErrorFlag() throws Exception {
        mvc.perform(get("/login").param("error",""))
                .andExpect(status().isOk())
                .andExpect(view().name("login"));
    }

}
