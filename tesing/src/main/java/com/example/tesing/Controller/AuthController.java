package com.example.tesing.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tesing.Entity.User;
import com.example.tesing.Repository.UserRepository;
import org.springframework.http.HttpStatus;
import jakarta.validation.Valid;
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserRepository userRepository;

    // Handles TC01, TC02, TC03, TC04
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUser(@Valid @RequestBody User user) {
        Map<String, String> response = new HashMap<>();

        // TC02: Check if email already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            response.put("error", "Email already exists");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // TC01: Save valid user
        userRepository.save(user);
        response.put("message", "Account created successfully");
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Handles TC05, TC06
    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> loginUser(@RequestBody User loginRequest) {
        Map<String, String> response = new HashMap<>();
        Optional<User> userOpt = userRepository.findByEmail(loginRequest.getEmail());

        // TC06: Check if user exists and password matches
        if (userOpt.isEmpty() || !userOpt.get().getPassword().equals(loginRequest.getPassword())) {
            response.put("error", "Denied: Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // TC05: Successful login
        response.put("message", "Redirecting to Dashboard");
        return ResponseEntity.ok(response);
    
}}