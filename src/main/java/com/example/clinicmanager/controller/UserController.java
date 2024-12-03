package com.example.clinicmanager.controller;

import com.example.clinicmanager.dto.UserDTO;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Tag(name = "User Management", description = "Endpoints for managing users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @GetMapping
    public ResponseEntity<List<UserEntity>> getAllUsers() {
        List<UserEntity> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Delete a user by ID", description = "Delete a specific user by their unique ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get all patients", description = "Retrieve a list of all registered patients with their ID and full name")
    @GetMapping("/patients")
    public ResponseEntity<List<UserDTO>> getAllPatients() {
        List<UserDTO> patients = userService.getUsersByRole(UserEntity.Role.PATIENT);
        if (patients.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no patients found
        }
        return ResponseEntity.ok(patients);
    }



}
