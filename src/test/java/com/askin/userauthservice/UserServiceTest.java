package com.askin.userauthservice;

import com.askin.userauthservice.model.User;
import com.askin.userauthservice.repository.UserRepository;
import com.askin.userauthservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterUser_ValidUser() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");

        // Mock repository
        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(user.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(user)).thenReturn(user);

        assertDoesNotThrow(() -> userService.registerUser(user));

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).findByEmail(user.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testRegisterUser_ExistingEmail() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");


        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
        when(userRepository.findByEmail(user.getEmail())).thenReturn(Optional.of(user));


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(user));


        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(userRepository, times(1)).findByEmail(user.getEmail());


        assertEquals("Email is already registered.", exception.getMessage());
    }

    @Test
    public void testRegisterUser_ExistingUsername() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password123");


        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));


        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.registerUser(user));


        verify(userRepository, times(1)).findByUsername(user.getUsername());


        assertEquals("Username is already taken.", exception.getMessage());
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");


        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(true);


        String token = userService.loginUser(user);


        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(passwordEncoder, times(1)).matches(user.getPassword(), user.getPassword());

        assertNotNull(token);
    }

    @Test
    public void testLoginUser_DoNotMatchPassword() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password123");

        when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        when(passwordEncoder.matches(user.getPassword(), user.getPassword())).thenReturn(false);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> userService.loginUser(user));

        verify(userRepository, times(1)).findByUsername(user.getUsername());
        verify(passwordEncoder, times(1)).matches(user.getPassword(), user.getPassword());

        assertNotNull(exception);
    }
}


