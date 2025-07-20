package com.expensivemanager.controller;

import com.expensivemanager.dto.CategoryDto;
import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import com.expensivemanager.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for managing expense categories.
 * All endpoints require authentication (JWT).
 * Categories can be user-specific or global.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Creates a new category for the authenticated user.
     *
     * @param user The authenticated user (injected by Spring Security)
     * @param dto  The category data (name)
     * @return The created category as a response
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(
            @AuthenticationPrincipal User user,
            @RequestBody @Valid CategoryDto dto) {
        Category category = categoryService.createCategory(user, dto);
        return ResponseEntity.ok(toDto(category));
    }

    /**
     * Retrieves all categories for the authenticated user,
     * including both user-specific and global categories.
     *
     * @param user The authenticated user
     * @return List of categories
     */
    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories(@AuthenticationPrincipal User user) {
        List<Category> categories = categoryService.getAllCategories(user);
        List<CategoryDto> dtos = categories.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

    /**
     * Retrieves a specific category by its ID, if accessible to the user.
     *
     * @param user The authenticated user
     * @param id   The category ID
     * @return The category
     */
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        Category category = categoryService.getCategoryById(user, id);
        return ResponseEntity.ok(toDto(category));
    }

    /**
     * Updates the name of a category owned by the user.
     *
     * @param user The authenticated user
     * @param id   The category ID
     * @param dto  The new category data
     * @return The updated category
     */
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDto> updateCategory(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestBody @Valid CategoryDto dto) {
        Category updated = categoryService.updateCategory(user, id, dto);
        return ResponseEntity.ok(toDto(updated));
    }

    /**
     * Deletes a user-owned category by its ID.
     *
     * @param user The authenticated user
     * @param id   The category ID
     * @return 204 No Content on success
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        categoryService.deleteCategory(user, id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Helper method to convert a Category entity to a DTO.
     *
     * @param category the entity
     * @return the DTO
     */
    private CategoryDto toDto(Category category) {
        CategoryDto dto = new CategoryDto();
        dto.setName(category.getName());
        return dto;
    }
}
