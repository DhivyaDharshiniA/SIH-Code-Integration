package com.example.springapp.controller;

import com.example.springapp.model.User;
import com.example.springapp.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Authorization header optional
    @PostMapping("/register")
    public User register(@RequestBody User user,
                         @RequestHeader(value = "Authorization", required = false) String token) {
        return userService.registerUser(user, token);
    }
}
