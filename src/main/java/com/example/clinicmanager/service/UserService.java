package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.UserDTO;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service class for managing users.
 */
@Service
@Tag(name = "User Service", description = "Service to manage user registration, retrieval, and deletion.")
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registers a new user.
     *
     * @param username  Username of the user.
     * @param password  Password of the user.
     * @param email     Email address of the user.
     * @param fullName  Full name of the user.
     * @param role      Role of the user.
     * @return The registered user.
     */
    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    public UserEntity registerUser(String username, String password, String email, String fullName, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        newUser.setEmail(email);
        newUser.setFullName(fullName);

        try {
            newUser.setRole(UserEntity.Role.valueOf(role.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        return userRepository.save(newUser);
    }

    /**
     * Retrieves a list of all users.
     *
     * @return List of users.
     */
    @Operation(summary = "Get all users", description = "Retrieves a list of all registered users.")
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Deletes a user by ID.
     *
     * @param id ID of the user.
     */
    @Operation(summary = "Delete a user by ID", description = "Deletes a user based on the provided user ID.")
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Retrieves a user by username.
     *
     * @param username Username of the user.
     * @return The user entity if found.
     */
    @Operation(summary = "Get user by username", description = "Fetches a user by their username.")
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Retrieves a user by ID.
     *
     * @param id ID of the user.
     * @return The user entity if found.
     */
    @Operation(summary = "Get user by ID", description = "Fetches a user by their unique ID.")
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Encodes a raw password.
     *
     * @param rawPassword The raw password to encode.
     * @return The encoded password.
     */
    @Operation(summary = "Encode password", description = "Encodes a raw password using the application's password encoder.")
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    public List<UserDTO> getUsersByRole(UserEntity.Role role) {
        return userRepository.findByRole(role).stream()
                .map(user -> new UserDTO(user.getUsername(), user.getEmail(), user.getFullName(), user.getRole().toString()))
                .collect(Collectors.toList());
    }

}
