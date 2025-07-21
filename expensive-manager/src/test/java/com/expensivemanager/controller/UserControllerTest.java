package com.expensivemanager.controller;

import com.expensivemanager.config.JwtAuthenticationFilter;
import com.expensivemanager.dto.UserRegistrationDto;
import com.expensivemanager.model.User;
import com.expensivemanager.service.JwtUtil;
import com.expensivemanager.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Web layer unit tests for UserController using MockMvc.
 */
@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean private JwtAuthenticationFilter jwtAuthenticationFilter;
    @MockBean private JwtUtil jwtUtil;


    /**
     * Test registering a new user (POST /api/users/register).
     */
    @Test
    void testRegisterUser() throws Exception {
        User user = new User();
        user.setId(10L);
        user.setUsername("krishna");
        user.setEmail("krishna@example.com");
        user.setRole("ROLE_USER");

        Mockito.when(userService.registerUser(any(UserRegistrationDto.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"username\": \"krishna\", \"email\": \"krishna@example.com\", \"password\": \"krish123\"}"))
            .andExpect(status().isOk());
    }
}
