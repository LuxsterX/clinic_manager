package com.example.clinicmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Schema(description = "Data Transfer Object for user registration requests")
public class RegisterRequest {

    @Schema(description = "Username for the new user", example = "john_doe")
    @NotBlank(message = "Username is required")
    private String username;

    @Schema(description = "Password for the new user", example = "SecureP@ssword123")
    @NotBlank(message = "Password is required")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
            message = "Password must be at least 8 characters long, include one uppercase letter, one number, and one special character"
    )
    private String password;

    @Schema(description = "Email address of the new user", example = "john.doe@example.com")
    @NotBlank(message = "Email is required")
    @Email(message = "Must be a valid email address")
    private String email;

    @Schema(description = "Full name of the new user", example = "John Doe")
    @NotBlank(message = "Full name is required")
    private String fullName;

    @Schema(description = "Role of the user", example = "PATIENT", allowableValues = {"ADMIN", "DOCTOR", "PATIENT"})
    @NotBlank(message = "Role is required")
    private String role;

    // Bezargumentowy konstruktor
    public RegisterRequest() {
    }

    // Getters and Setters
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
