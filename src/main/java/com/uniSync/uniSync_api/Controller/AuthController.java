package com.uniSync.uniSync_api.Controller;

import com.uniSync.uniSync_api.Model.User;
import com.uniSync.uniSync_api.Repository.UserRepository;
import com.uniSync.uniSync_api.utils.HashUtil;
import com.uniSync.uniSync_api.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        User user = userRepository.findAll().stream()
            .filter(u -> u.getEmail().equals(email))
            .findFirst()
            .orElse(null);

        if (user != null && HashUtil.verifyPassword(password, user.getPasswordHash())) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }
}
