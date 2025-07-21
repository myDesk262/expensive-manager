package com.expensivemanager.repository;

import com.expensivemanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for UserRepository using in-memory database.
 */
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindByUsername() {
        User user = new User();
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("ROLE_USER");
        userRepository.save(user);

        Optional<User> result = userRepository.findByUsername("testuser");
        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void testExistsByUsernameAndEmail() {
        User user = new User();
        user.setUsername("duplicate");
        user.setEmail("duplicate@example.com");
        user.setPassword("dup");
        user.setRole("ROLE_USER");
        userRepository.save(user);

        assertTrue(userRepository.existsByUsername("duplicate"));
        assertTrue(userRepository.existsByEmail("duplicate@example.com"));
        assertFalse(userRepository.existsByUsername("notfound"));
    }
}
