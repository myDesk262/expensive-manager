package com.expensivemanager.repository;

import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for CategoryRepository using in-memory database.
 */
@DataJpaTest
class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    void testSaveAndFindByUserOrUserIsNull() {
        User user = new User();
        user.setUsername("krishna");
        user.setEmail("k@example.com");
        user.setPassword("pass");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Category cat1 = new Category();
        cat1.setName("Food");
        cat1.setUser(user);
        categoryRepository.save(cat1);

        Category cat2 = new Category();
        cat2.setName("GlobalCat");
        cat2.setUser(null);
        categoryRepository.save(cat2);

        List<Category> result = categoryRepository.findByUserOrUserIsNull(user);
        assertEquals(2, result.size());
    }

    @Test
    void testExistsByNameAndUser() {
        User user = new User();
        user.setUsername("krishna2");
        user.setEmail("k2@example.com");
        user.setPassword("pass2");
        user.setRole("ROLE_USER");
        user = userRepository.save(user);

        Category cat = new Category();
        cat.setName("Books");
        cat.setUser(user);
        categoryRepository.save(cat);

        assertTrue(categoryRepository.existsByNameAndUser("Books", user));
        assertFalse(categoryRepository.existsByNameAndUser("NotExist", user));
    }
}
