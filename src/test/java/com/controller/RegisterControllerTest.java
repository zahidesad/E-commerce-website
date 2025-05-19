package com.controller;

import com.model.User;
import com.service.UserService;
import com.service.TCNumberVerificationService;
import com.service.EmailService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Unit / slice tests for RegisterController using standalone MockMvc.
 */
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

    @Mock  UserService userService;
    @Mock  TCNumberVerificationService tcService;
    @Mock  EmailService emailService;

    // A dummy View to satisfy InternalResourceViewResolver when returning "register"
    @Mock  View stubView;

    @InjectMocks
    RegisterController registerController;

    private MockMvc mockMvc;

    void setupMockMvc() {
        mockMvc = MockMvcBuilders.standaloneSetup(registerController)
                .setSingleView(stubView)
                .build();
    }

    /* --------------------------------------------------------
     * 1) GET /register
     * ------------------------------------------------------ */
    @Test
    @DisplayName("GET /register returns view with empty form")
    void getRegisterPage() throws Exception {
        setupMockMvc();

        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("registerForm"));
    }

    /* --------------------------------------------------------
     * 2) Successful registration
     * ------------------------------------------------------ */
    @Test
    @DisplayName("POST /signup with valid form succeeds")
    void postRegister_success() throws Exception {
        setupMockMvc();

        when(userService.isEmailAlreadyInUse("alice@test.com")).thenReturn(false);
        when(tcService.verify(11111111110L, "Alice", "Smith", 1990)).thenReturn(true);
        when(userService.register(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0)); // return same user

        mockMvc.perform(post("/register")
                        .param("name", "Alice")
                        .param("surname", "Smith")
                        .param("email", "alice@test.com")
                        .param("password", "secret")
                        .param("confirmPassword", "secret")
                        .param("tcNumber", "11111111110")
                        .param("birthYear", "1990"))
                .andExpect(status().isOk())
                .andExpect(view().name("redirect:/login"));

        /* verify interactions */
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);
        verify(userService).register(userCaptor.capture());
        assertThat(userCaptor.getValue().isEnabled()).isFalse();

        verify(emailService).sendVerificationEmail(eq(userCaptor.getValue()), anyString());
    }

    /* --------------------------------------------------------
     * 3) Duplicate e-mail
     * ------------------------------------------------------ */
    @Test
    @DisplayName("POST /signup fails when e-mail already used")
    void postRegister_duplicateEmail() throws Exception {
        setupMockMvc();

        mockMvc.perform(post("/register")
                        .param("name", "Bob")
                        .param("surname", "Smith")
                        .param("email", "bob@test.com")
                        .param("password", "secret")
                        .param("confirmPassword", "secret")
                        .param("tcNumber", "11111111110")
                        .param("birthYear", "1990"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"));

        verifyNoInteractions(emailService);
        verify(userService, never()).register(any());
    }

    /* --------------------------------------------------------
     * 4) Invalid TC number
     * ------------------------------------------------------ */
    @Test
    @DisplayName("POST /signup fails when TC verification fails")
    void postRegister_invalidTc() throws Exception {
        setupMockMvc();

        when(tcService.verify(anyLong(), anyString(), anyString(), anyInt())).thenReturn(false);

        mockMvc.perform(post("/register")
                        .param("name", "Eve")
                        .param("surname", "Smith")
                        .param("email", "eve@test.com")
                        .param("password", "secret")
                        .param("confirmPassword", "secret")
                        .param("tcNumber", "00000000000")
                        .param("birthYear", "1990"))
                .andExpect(status().isOk())
                .andExpect(view().name("signup"))
                .andExpect(model().attributeExists("msg"));          // controller puts a generic error msg


        verifyNoInteractions(emailService);
        verify(userService, never()).register(any());
    }
}

