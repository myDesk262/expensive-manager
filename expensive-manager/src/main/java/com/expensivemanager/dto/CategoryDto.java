package com.expensivemanager.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object for category creation and update requests.
 */
@Getter @Setter
public class CategoryDto {
    @NotBlank(message = "Category name is required")
    private String name;
}
