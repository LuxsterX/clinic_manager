package com.example.clinicmanager.controller;

import com.example.clinicmanager.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        String encodedPassword = userService.encodePassword(password);
        // Logika rejestracji użytkownika
        return "User registered with encoded password: " + encodedPassword;
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Logika logowania użytkownika
        return "User logged in";
    }
}
