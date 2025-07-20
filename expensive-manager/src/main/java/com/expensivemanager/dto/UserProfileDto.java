package com.expensivemanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Data Transfer Object for user profile information.
 */
@Data
@AllArgsConstructor
public class UserProfileDto {
    private Long id;
    private String username;
    private String email;
    private String role;
}
