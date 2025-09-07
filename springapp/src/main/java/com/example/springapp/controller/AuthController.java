package com.example.springapp.controller;

import com.example.springapp.model.User;
import com.example.springapp.repository.UserRepository;
import com.example.springapp.security.JwtService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthController(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    // ✅ Only login is public
    @PostMapping("/login")
    public String login(@RequestBody User user) {
        User dbUser = userRepository.findByAbhaId(user.getAbhaId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return jwtService.generateToken(dbUser.getAbhaId(), dbUser.getRole().name());
    }
}
