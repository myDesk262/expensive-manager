package com.expensivemanager.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for JwtUtil utility class.
 * Tests token generation, extraction, and validation methods.
 */
class JwtUtilTest {

    private JwtUtil jwtUtil;

    /**
     * Sets up a JwtUtil instance with known secret and expiration.
     */
    @BeforeEach
    void setUp() {
        jwtUtil = new JwtUtil();
        // Inject the secret and expiration using reflection (since @Value is not set in plain unit tests)
        ReflectionTestUtils.setField(jwtUtil, "jwtSecret", "Gam-Goum-Ganapathaye-Namaha&&_Krishnam_vande_Jagadguru1111**_JaiShreeRam2222_RaadheKrishna3333@@_ShivarpanaMastu4444##-krishnarpanamastu5555-Dum-Dhurgaya-Namaha");
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", 3600000L); // 1 hour
    }

    /**
     * Tests that a token can be generated and validated, and username extracted correctly.
     */
    @Test
    void testGenerateAndValidateToken() {
        String username = "testuser";
        String token = jwtUtil.generateToken(username);

        // Extract username from token and validate token
        assertEquals(username, jwtUtil.extractUsername(token));
        assertTrue(jwtUtil.validateToken(token));
        assertFalse(jwtUtil.isTokenExpired(token));
    }

   /**
     * Tests token expiration handling by asserting that parsing an expired token throws an exception.
     */
    @Test
    void testExpiredToken() {
        // Set expiration in the past
        ReflectionTestUtils.setField(jwtUtil, "jwtExpirationMs", -1000L);
        String token = jwtUtil.generateToken("expireduser");

        assertTrue(jwtUtil.isTokenExpired(token));
    }


    /**
     * Tests token generation using UserDetails, with authorities as roles.
     */
    @Test
    void testGenerateTokenWithUserDetails() {
        UserDetails userDetails = User.withUsername("john")
                .password("pw")
                .authorities(Collections.singletonList(() -> "ROLE_USER"))
                .build();

        String token = jwtUtil.generateToken(userDetails);
        assertNotNull(token);

        String username = jwtUtil.extractUsername(token);
        assertEquals("john", username);
        assertTrue(jwtUtil.validateToken(token));
    }
}
