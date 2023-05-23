package com.askin.userauthservice;
import com.askin.userauthservice.controller.UserController;
import com.askin.userauthservice.model.User;
import com.askin.userauthservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;


public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegisterUser() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");


        doNothing().when(userService).registerUser(user);


        ResponseEntity<String> response = userController.registerUser(user);


        verify(userService, times(1)).registerUser(user);


        assert response.getStatusCode() == HttpStatus.OK;

        assert response.getBody().equals("User registration is completed successfully.");
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");


        when(userService.loginUser(user)).thenReturn("token123");


        ResponseEntity<String> response = userController.loginUser(user);


        verify(userService, times(1)).loginUser(user);


        assert response.getStatusCode() == HttpStatus.OK;

        assert response.getBody().equals("token123");
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");


        when(userService.loginUser(user)).thenReturn(null);


        ResponseEntity<String> response = userController.loginUser(user);


        verify(userService, times(1)).loginUser(user);


        assert response.getStatusCode() == HttpStatus.UNAUTHORIZED;

        assert response.getBody().equals("Invalid username or password.");
    }


}
