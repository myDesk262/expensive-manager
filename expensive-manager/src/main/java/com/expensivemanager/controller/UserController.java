package com.expensivemanager.controller;

import com.expensivemanager.dto.UserLoginDto;
import com.expensivemanager.dto.UserProfileDto;
import com.expensivemanager.dto.UserRegistrationDto;
import com.expensivemanager.model.User;
import com.expensivemanager.service.UserService;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody @Valid UserRegistrationDto dto) {
        try {
            User user = userService.registerUser(dto);
            return ResponseEntity.ok("User registered successfully! ID: " + user.getId());
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid UserLoginDto dto) {
        try {
            String jwt = userService.login(dto);
            return ResponseEntity.ok(Map.of("token", jwt)); // Cleaner JSON response
        } catch (RuntimeException ex) {
            return ResponseEntity.status(401).body(ex.getMessage());
        }
    }
    
    /**
     * Retrieves the profile information of the currently authenticated user.
     *
     * @param user the authenticated user injected by Spring Security
     * @return the user's profile details (id, username, email, role)
     */
    @GetMapping("/me")
    public ResponseEntity<UserProfileDto> getProfile(@AuthenticationPrincipal User user) {
        UserProfileDto dto = new UserProfileDto(
            user.getId(),
            user.getUsername(),
            user.getEmail(),
            user.getRole()
        );
        return ResponseEntity.ok(dto);
    }


}
