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

        // Mock service
        doNothing().when(userService).registerUser(user);

        // Call the method under test
        ResponseEntity<String> response = userController.registerUser(user);

        // Verify the service method is called
        verify(userService, times(1)).registerUser(user);

        // Verify the response status code is OK
        assert response.getStatusCode() == HttpStatus.OK;
        // Verify the response message
        assert response.getBody().equals("User registration is completed successfully.");
    }

    @Test
    public void testLoginUser_ValidCredentials() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        // Mock service
        when(userService.loginUser(user)).thenReturn("token123");

        // Call the method under test
        ResponseEntity<String> response = userController.loginUser(user);

        // Verify the service method is called
        verify(userService, times(1)).loginUser(user);

        // Verify the response status code is OK
        assert response.getStatusCode() == HttpStatus.OK;
        // Verify the response body contains the token
        assert response.getBody().equals("token123");
    }

    @Test
    public void testLoginUser_InvalidCredentials() {
        // Test data
        User user = new User();
        user.setUsername("testuser");
        user.setPassword("password");

        // Mock service
        when(userService.loginUser(user)).thenReturn(null);

        // Call the method under test
        ResponseEntity<String> response = userController.loginUser(user);

        // Verify the service method is called
        verify(userService, times(1)).loginUser(user);

        // Verify the response status code is UNAUTHORIZED
        assert response.getStatusCode() == HttpStatus.UNAUTHORIZED;
        // Verify the response body contains the error message
        assert response.getBody().equals("Invalid username or password.");
    }


}
