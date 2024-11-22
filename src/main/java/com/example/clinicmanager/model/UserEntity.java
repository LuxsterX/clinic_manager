package com.example.clinicmanager.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Entity
@Table(name = "users")
@Schema(description = "Details of a user")
public class UserEntity {

    public enum Role {
        ADMIN,
        DOCTOR,
        PATIENT
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the user", example = "1")
    private Long id;

    @NotBlank
    @Column(nullable = false, unique = true)
    @Schema(description = "Username of the user", example = "john_doe")
    private String username;

    @NotBlank
    @Column(nullable = false)
    @Schema(description = "Password of the user")
    private String password;

    @NotBlank
    @Email
    @Column(nullable = false, unique = true)
    @Schema(description = "Email address of the user", example = "john.doe@example.com")
    private String email;

    @NotBlank
    @Column(nullable = false, name = "full_name")
    @Schema(description = "Full name of the user", example = "John Doe")
    private String fullName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(description = "Role of the user", example = "PATIENT")
    private Role role;

    @OneToMany(mappedBy = "patient")
    @Schema(description = "Appointments where the user is a patient")
    private Set<AppointmentEntity> appointmentsAsPatient;

    @OneToMany(mappedBy = "doctor")
    @Schema(description = "Appointments where the user is a doctor")
    private Set<AppointmentEntity> appointmentsAsDoctor;

    // Domyślny konstruktor (wymagany przez Hibernate)
    public UserEntity() {}

    // Konstruktor z wszystkimi polami
    public UserEntity(String username, String password, Role role, String email, String fullName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.fullName = fullName;
    }

    // Gettery i settery

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<AppointmentEntity> getAppointmentsAsPatient() {
        return appointmentsAsPatient;
    }

    public void setAppointmentsAsPatient(Set<AppointmentEntity> appointmentsAsPatient) {
        this.appointmentsAsPatient = appointmentsAsPatient;
    }

    public Set<AppointmentEntity> getAppointmentsAsDoctor() {
        return appointmentsAsDoctor;
    }

    public void setAppointmentsAsDoctor(Set<AppointmentEntity> appointmentsAsDoctor) {
        this.appointmentsAsDoctor = appointmentsAsDoctor;
    }
}
