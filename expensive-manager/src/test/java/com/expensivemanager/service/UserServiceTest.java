package com.expensivemanager.service;

import com.expensivemanager.dto.UserLoginDto;
import com.expensivemanager.dto.UserRegistrationDto;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for UserService.
 */
class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("krishna");
        testUser.setEmail("krishna@example.com");
        testUser.setPassword("$2a$10$...");
        testUser.setRole("ROLE_USER");
    }

    /**
     * Test successful user registration.
     */
    @Test
    void testRegisterUser_Success() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername("krishna");
        dto.setEmail("krishna@example.com");
        dto.setPassword("krish123");

        when(userRepository.existsByUsername("krishna")).thenReturn(false);
        when(userRepository.existsByEmail("krishna@example.com")).thenReturn(false);
        when(passwordEncoder.encode("krish123")).thenReturn("hashed_pass");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User result = userService.registerUser(dto);

        assertNotNull(result);
        assertEquals("krishna", result.getUsername());
        assertEquals("krishna@example.com", result.getEmail());
        assertEquals("ROLE_USER", result.getRole());
        verify(userRepository).save(any(User.class));
    }

    /**
     * Test registration fails for duplicate username.
     */
    @Test
    void testRegisterUser_DuplicateUsername() {
        UserRegistrationDto dto = new UserRegistrationDto();
        dto.setUsername("krishna");
        dto.setEmail("krishna@example.com");
        dto.setPassword("krish123");

        when(userRepository.existsByUsername("krishna")).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> userService.registerUser(dto));
        assertEquals("Username already exists", ex.getMessage());
    }

    /**
     * Test successful login returns JWT.
     */
    @Test
    void testLogin_Success() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername("krishna");
        dto.setPassword("krish123");

        when(userRepository.findByUsername("krishna")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("krish123", "$2a$10$...")).thenReturn(true);
        when(jwtUtil.generateToken("krishna")).thenReturn("jwt_token");

        String result = userService.login(dto);

        assertEquals("jwt_token", result);
    }

    /**
     * Test login fails with wrong password.
     */
    @Test
    void testLogin_InvalidPassword() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername("krishna");
        dto.setPassword("wrongpass");

        when(userRepository.findByUsername("krishna")).thenReturn(Optional.of(testUser));
        when(passwordEncoder.matches("wrongpass", "$2a$10$...")).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> userService.login(dto));
        assertEquals("Invalid username or password", ex.getMessage());
    }

    /**
     * Test login fails if user not found.
     */
    @Test
    void testLogin_UserNotFound() {
        UserLoginDto dto = new UserLoginDto();
        dto.setUsername("notfound");
        dto.setPassword("any");

        when(userRepository.findByUsername("notfound")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> userService.login(dto));
        assertEquals("Invalid username or password", ex.getMessage());
    }
}
