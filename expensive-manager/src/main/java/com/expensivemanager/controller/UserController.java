package com.expensivemanager.controller;

import com.expensivemanager.dto.UserLoginDto;
import com.expensivemanager.dto.UserRegistrationDto;
import com.expensivemanager.model.User;
import com.expensivemanager.service.UserService;
import jakarta.validation.Valid;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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


}
