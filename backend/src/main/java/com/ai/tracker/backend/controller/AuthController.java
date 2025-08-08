package com.ai.tracker.backend.controller;

import com.ai.tracker.backend.model.User;
import com.ai.tracker.backend.repository.UserRepository;
import com.ai.tracker.backend.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public String register(@RequestBody User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User registered successfully";
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
        System.out.println("LOGIN ATTEMPT: " + user.getUsername());

        try {
            User existingUser = userRepository.findByUsername(user.getUsername())
                    .orElseThrow(() -> new RuntimeException("User not found"));

            System.out.println("DB USER FOUND: " + existingUser.getUsername());
            System.out.println("RAW PASSWORD: " + user.getPassword());
            System.out.println("ENCODED PASSWORD IN DB: " + existingUser.getPassword());

            if (!passwordEncoder.matches(user.getPassword(), existingUser.getPassword())) {
                System.out.println("❌ Password does not match");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(Map.of("error", "Invalid credentials"));
            }

            String token = jwtUtil.generateToken(existingUser.getUsername());
            System.out.println("✅ TOKEN GENERATED: " + token);

            return ResponseEntity.ok(Map.of("token", token));

        } catch (RuntimeException e) {
            System.out.println("❌ ERROR: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", e.getMessage()));
        }
    }

}
