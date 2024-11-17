package com.example.clinicmanager.service;

import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Rejestracja nowego użytkownika.
     *
     * @param username  nazwa użytkownika.
     * @param password  hasło użytkownika.
     * @param email     adres e-mail użytkownika.
     * @param fullName  pełne imię i nazwisko użytkownika.
     * @param role      rola użytkownika.
     * @return zarejestrowany użytkownik.
     */
    public UserEntity registerUser(String username, String password, String email, String fullName, String role) {
        if (userRepository.findByUsername(username).isPresent()) {
            throw new IllegalArgumentException("Username already exists");
        }

        if (userRepository.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Tworzymy nowego użytkownika
        UserEntity newUser = new UserEntity();
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password)); // Hasło jest szyfrowane
        newUser.setEmail(email);
        newUser.setFullName(fullName);

        try {
            newUser.setRole(UserEntity.Role.valueOf(role.toUpperCase())); // Rola jest konwertowana do Enum
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid role: " + role);
        }

        // Zapisujemy użytkownika w bazie danych
        return userRepository.save(newUser);
    }

    /**
     * Pobiera listę wszystkich użytkowników.
     *
     * @return lista użytkowników.
     */
    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Usuwanie użytkownika po ID.
     *
     * @param id ID użytkownika.
     */
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
    }

    /**
     * Pobieranie użytkownika po nazwie użytkownika.
     *
     * @param username nazwa użytkownika.
     * @return obiekt użytkownika (jeśli istnieje).
     */
    public Optional<UserEntity> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Pobieranie użytkownika po ID.
     *
     * @param id ID użytkownika.
     * @return obiekt użytkownika (jeśli istnieje).
     */
    public Optional<UserEntity> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }
}
