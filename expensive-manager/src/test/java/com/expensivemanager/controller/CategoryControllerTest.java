package com.expensivemanager.controller;

import com.expensivemanager.config.JwtAuthenticationFilter;
import com.expensivemanager.dto.CategoryDto;
import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.UserRepository;
import com.expensivemanager.service.CategoryService;
import com.expensivemanager.service.JwtUtil;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

/**
 * Web layer unit tests for CategoryController using MockMvc.
 */
@WebMvcTest(CategoryController.class)
@AutoConfigureMockMvc(addFilters = false) 
class CategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean 
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    
    @MockBean 
    private JwtUtil jwtUtil;

    @MockBean
    private UserRepository userRepository; // <-- add this for principal lookup

    /**
     * Test creating a new category (POST /api/categories).
     */
    @Test
    @WithMockUser(username = "krishna", roles = "USER") 
    void testCreateCategory() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUsername("krishna");

        CategoryDto dto = new CategoryDto();
        dto.setName("Food");
        Category cat = new Category();
        cat.setId(100L);
        cat.setName("Food");
        cat.setUser(user);

        // Mock repository lookup for principal
        when(userRepository.findByUsername("krishna")).thenReturn(Optional.of(user));
        when(categoryService.createCategory(any(User.class), any(CategoryDto.class))).thenReturn(cat);

        mockMvc.perform(post("/api/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\": \"Food\"}")
                .principal(() -> "krishna"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name").value("Food"));
    }
}

