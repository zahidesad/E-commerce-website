package com.service;

import com.model.User;
import com.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for {@link UserService}.
 *
 * Test Dependencies : Mockito, JUnit-Jupiter
 * Test Control      : Spring context is NOT started; collaborators are mocked.
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;   // class under test

    private final String EMAIL = "alice@test.com";


    /* ---------------------------------------------------------------------
     * 1) isEmailAlreadyInUse
     * ------------------------------------------------------------------- */
    @Test
    @DisplayName("isEmailAlreadyInUse() returns true when repository finds email")
    void isEmailAlreadyInUse_returnsTrue_whenEmailExists() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(new User()));

        boolean result = userService.isEmailAlreadyInUse(EMAIL);

        assertThat(result).isTrue();
        verify(userRepository).findByEmail(EMAIL);
    }

    @Test
    @DisplayName("isEmailAlreadyInUse() returns false when email not found")
    void isEmailAlreadyInUse_returnsFalse_whenEmailMissing() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        boolean result = userService.isEmailAlreadyInUse(EMAIL);

        assertThat(result).isFalse();
        verify(userRepository).findByEmail(EMAIL);
    }

    /* ---------------------------------------------------------------------
     * 2) register
     * ------------------------------------------------------------------- */
    @Test
    @DisplayName("register() encrypts password and saves user")
    void register_encryptsPassword_andPersistsUser() {
        when(passwordEncoder.encode(any())).thenReturn("ENCODED");

        // given
        User user = new User();
        user.setEmail(EMAIL);
        user.setPassword("PLAIN");

        when(userRepository.save(any(User.class)))
                .thenAnswer(inv -> inv.getArgument(0)); // return same instance

        // when
        User saved = userService.register(user);

        // then
        assertThat(saved.getPassword()).isEqualTo("ENCODED");
        verify(passwordEncoder).encode("PLAIN");
        verify(userRepository).save(saved);
    }

    /* ---------------------------------------------------------------------
     * 3) login
     * ------------------------------------------------------------------- */
    @Nested
    class Login {

        @Test
        void login_returnsUser_whenCredentialsCorrect() {
            User user = new User();
            user.setEmail(EMAIL);
            user.setPassword("PLAIN");              // ①

            when(userRepository.findByEmail(EMAIL))
                    .thenReturn(Optional.of(user));

            User result = userService.login(EMAIL, "PLAIN");  // ②

            assertThat(result).isSameAs(user);
            verify(userRepository).findByEmail(EMAIL);
            verifyNoInteractions(passwordEncoder);            // ③
        }

        @Test
        void login_returnsNull_whenPasswordIncorrect() {
            User user = new User();
            user.setEmail(EMAIL);
            user.setPassword("CORRECT");

            when(userRepository.findByEmail(EMAIL))
                    .thenReturn(Optional.of(user));

            User result = userService.login(EMAIL, "WRONG");

            assertThat(result).isNull();
        }

        @Test
        void login_returnsNull_whenEmailNotFound() {
            when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

            User result = userService.login(EMAIL, "ANY");

            assertThat(result).isNull();
        }
    }

    /* ---------------------------------------------------------------------
     * 4) findByVerificationCode
     * ------------------------------------------------------------------- */
    @Test
    void findByVerificationCode_returnsUser_whenCodeExists() {
        User user = new User();
        when(userRepository.findByVerificationCode("CODE"))
                .thenReturn(Optional.of(user));

        Optional<User> result = userService.findByVerificationCode("CODE");

        assertThat(result).containsSame(user);
    }

    /* ---------------------------------------------------------------------
     * 5) isAccountEnabled
     * ------------------------------------------------------------------- */
    @Test
    void isAccountEnabled_returnsFalse_whenDisabled() {
        User disabled = new User();
        disabled.setEnabled(false);
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.of(disabled));

        Boolean enabled = userService.isAccountEnabled(EMAIL);

        assertThat(enabled).isFalse();
    }

    @Test
    void isAccountEnabled_returnsNull_whenUserMissing() {
        when(userRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());

        Boolean enabled = userService.isAccountEnabled(EMAIL);

        assertThat(enabled).isNull();
    }
}
