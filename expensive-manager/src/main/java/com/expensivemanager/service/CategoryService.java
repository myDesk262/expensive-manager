package com.expensivemanager.service;

import com.expensivemanager.dto.CategoryDto;
import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing expense categories.
 * Handles creation, retrieval, update, and deletion of categories.
 * Categories can be user-specific or global (system categories).
 */
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    /**
     * Creates a new category for the specified user.
     * Throws a RuntimeException if a category with the same name already exists for this user.
     *
     * @param user The authenticated user creating the category.
     * @param dto The category data transfer object containing the name.
     * @return The created Category entity.
     */
    public Category createCategory(User user, CategoryDto dto) {
        if (categoryRepository.existsByNameAndUser(dto.getName(), user)) {
            throw new RuntimeException("Category already exists for this user");
        }
        Category cat = new Category();
        cat.setName(dto.getName());
        cat.setUser(user);
        return categoryRepository.save(cat);
    }

    /**
     * Retrieves all categories for a user, including global categories.
     *
     * @param user The user whose categories are to be fetched.
     * @return List of Category entities (both user and global categories).
     */
    public List<Category> getAllCategories(User user) {
        return categoryRepository.findByUserOrUserIsNull(user);
    }

    /**
     * Retrieves a specific category by ID for a user.
     * Only allows access if the category belongs to the user or is global.
     *
     * @param user The authenticated user.
     * @param id   The category ID.
     * @return The Category entity if found, else throws RuntimeException.
     */
    public Category getCategoryById(User user, Long id) {
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            if (category.getUser() == null || category.getUser().getId().equals(user.getId())) {
                return category;
            }
        }
        throw new RuntimeException("Category not found or not accessible");
    }

    /**
     * Updates the name of a category for a user.
     * Only allows updates for categories owned by the user.
     *
     * @param user The authenticated user.
     * @param id   The category ID.
     * @param dto  The category data with the new name.
     * @return The updated Category entity.
     */
    public Category updateCategory(User user, Long id, CategoryDto dto) {
        Category category = getCategoryById(user, id);
        if (category.getUser() == null) {
            throw new RuntimeException("Cannot update a system (global) category");
        }
        if (categoryRepository.existsByNameAndUser(dto.getName(), user)) {
            throw new RuntimeException("Category with this name already exists");
        }
        category.setName(dto.getName());
        return categoryRepository.save(category);
    }

    /**
     * Deletes a category owned by the user.
     * Does not allow deleting global categories.
     *
     * @param user The authenticated user.
     * @param id   The category ID.
     */
    public void deleteCategory(User user, Long id) {
        Category category = getCategoryById(user, id);
        if (category.getUser() == null) {
            throw new RuntimeException("Cannot delete a system (global) category");
        }
        categoryRepository.delete(category);
    }
}
