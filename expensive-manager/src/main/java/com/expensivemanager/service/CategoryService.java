package com.expensivemanager.service;

import com.expensivemanager.dto.CategoryDto;
import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import com.expensivemanager.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(User user, CategoryDto dto) {
        if (categoryRepository.existsByNameAndUser(dto.getName(), user)) {
            throw new RuntimeException("Category already exists for this user");
        }
        Category cat = new Category();
        cat.setName(dto.getName());
        cat.setUser(user);
        return categoryRepository.save(cat);
    }
    // Add list/get/delete/update as needed
}
