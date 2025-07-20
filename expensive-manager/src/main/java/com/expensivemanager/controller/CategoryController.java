package com.expensivemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.expensivemanager.dto.CategoryDto;
import com.expensivemanager.model.Category;
import com.expensivemanager.model.User;
import com.expensivemanager.service.CategoryService;

import jakarta.validation.Valid;

/**
 * REST controller for category management.
 * All endpoints require authentication.
 */
@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal User user, @RequestBody @Valid CategoryDto dto) {
        Category cat = categoryService.createCategory(user, dto);
        return ResponseEntity.ok(cat); // Or use a response DTO
    }
    // Add list/get/delete/update endpoints
}
