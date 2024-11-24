package com.example.clinicmanager.controller;

import com.example.clinicmanager.dto.AppointmentDTO;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.UserRepository;
import com.example.clinicmanager.security.JwtUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication", description = "Endpoints for user authentication")
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    @Operation(summary = "Log in a user", description = "Authenticates a user and returns a JWT token")
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        UserEntity user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            return ResponseEntity.status(401).body("Invalid username or password");
        }

        // Generate JWT token with username and single role
        String token = jwtUtils.generateToken(user.getUsername(), user.getRole().toString());
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("role", user.getRole().toString());

        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Register a new doctor", description = "Registers a new doctor account")
    @PostMapping("/register/doctor")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> registerDoctor(@RequestBody RegisterRequest registerRequest) {
        return registerUser(registerRequest, UserEntity.Role.DOCTOR);
    }

    @Operation(summary = "Register a new patient", description = "Registers a new patient account")
    @PostMapping("/register/patient")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> registerPatient(@RequestBody RegisterRequest registerRequest) {
        return registerUser(registerRequest, UserEntity.Role.PATIENT);
    }


    private ResponseEntity<String> registerUser(RegisterRequest registerRequest, UserEntity.Role role) {
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            return ResponseEntity.badRequest().body("Username already exists");
        }

        if (userRepository.findByEmail(registerRequest.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(registerRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        newUser.setEmail(registerRequest.getEmail());
        newUser.setFullName(registerRequest.getFullName());
        newUser.setRole(role);

        userRepository.save(newUser);
        return ResponseEntity.ok(role.name() + " registered successfully");
    }

    public static class LoginRequest {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public static class RegisterRequest {
        private String username;
        private String password;
        private String email;
        private String fullName;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }
    }
}
