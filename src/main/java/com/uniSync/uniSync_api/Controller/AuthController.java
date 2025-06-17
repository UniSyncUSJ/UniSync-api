package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Model.User;
import com.uniSync.uniSync_api.Repository.UserRepository;
import com.uniSync.uniSync_api.utils.HashUtil;
import com.uniSync.uniSync_api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        logger.info("Login attempt for email: {}", email);
        
        User user = userRepository.findAll().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);

        if (user == null) {
            logger.warn("User not found with email: {}", email);
            return ResponseEntity.status(401).body("User not found");
        }

        logger.info("User found, verifying password");
        boolean passwordValid = HashUtil.verifyPassword(password, user.getPasswordHash());
        logger.info("Password verification result: {}", passwordValid);

        if (passwordValid) {
            String token = jwtUtil.generateToken(user.getEmail());
            logger.info("Login successful for user: {}", email);
            return ResponseEntity.ok(token);
        } else {
            logger.warn("Invalid password for user: {}", email);
            return ResponseEntity.status(401).body("Invalid password");
        }
    }
}
