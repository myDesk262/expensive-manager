package com.expensivemanager.service;

import com.expensivemanager.dto.CategoryDto;
import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.CategoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CategoryService.
 * Uses Mockito to mock dependencies.
 */
class CategoryServiceTest {

    @InjectMocks
    private CategoryService categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    private User testUser;
    private Category testCategory;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        testUser = new User();
        testUser.setId(1L);
        testUser.setUsername("krishna");
        testCategory = new Category();
        testCategory.setId(10L);
        testCategory.setName("Books");
        testCategory.setUser(testUser);
    }

    /**
     * Test creation of a new category.
     */
    @Test
    void testCreateCategory_Success() {
        CategoryDto dto = new CategoryDto();
        dto.setName("Books");
        when(categoryRepository.existsByNameAndUser("Books", testUser)).thenReturn(false);
        when(categoryRepository.save(any(Category.class))).thenReturn(testCategory);

        Category result = categoryService.createCategory(testUser, dto);

        assertNotNull(result);
        assertEquals("Books", result.getName());
        assertEquals(testUser, result.getUser());
        verify(categoryRepository).save(any(Category.class));
    }

    /**
     * Test creation fails if category name already exists for the user.
     */
    @Test
    void testCreateCategory_AlreadyExists() {
        CategoryDto dto = new CategoryDto();
        dto.setName("Books");
        when(categoryRepository.existsByNameAndUser("Books", testUser)).thenReturn(true);

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> categoryService.createCategory(testUser, dto));

        assertEquals("Category already exists for this user", ex.getMessage());
    }

    /**
     * Test retrieval of all categories for user (user and global).
     */
    @Test
    void testGetAllCategories() {
        List<Category> categories = Arrays.asList(testCategory);
        when(categoryRepository.findByUserOrUserIsNull(testUser)).thenReturn(categories);

        List<Category> result = categoryService.getAllCategories(testUser);

        assertEquals(1, result.size());
        assertEquals("Books", result.get(0).getName());
    }

    /**
     * Test deletion of user-owned category.
     */
    @Test
    void testDeleteCategory_Success() {
        when(categoryRepository.findById(10L)).thenReturn(Optional.of(testCategory));
        categoryService.deleteCategory(testUser, 10L);
        verify(categoryRepository).delete(testCategory);
    }

    /**
     * Test cannot delete global/system category.
     */
    @Test
    void testDeleteCategory_GlobalCategory() {
        Category globalCat = new Category();
        globalCat.setId(20L);
        globalCat.setName("Rent");
        globalCat.setUser(null);

        when(categoryRepository.findById(20L)).thenReturn(Optional.of(globalCat));

        RuntimeException ex = assertThrows(RuntimeException.class,
            () -> categoryService.deleteCategory(testUser, 20L));
        assertEquals("Cannot delete a system (global) category", ex.getMessage());
    }
}
