package com.example.clinicmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing an appointment.
 */
@Entity
@Schema(description = "Entity representing an appointment in the clinic")
@Table(name = "appointments")
public class AppointmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the appointment", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @Schema(description = "Patient linked to the appointment", example = "UserEntity object representing the patient")
    private UserEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @Schema(description = "Doctor linked to the appointment", example = "UserEntity object representing the doctor")
    private UserEntity doctor;

    @Column(nullable = false)
    @Schema(description = "Date and time of the appointment", example = "2023-12-01T15:30:00")
    private LocalDateTime dateTime;

    @Column(length = 255)
    @Schema(description = "Details or notes about the appointment", example = "Routine check-up")
    private String details;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Schema(
            description = "Status of the appointment",
            example = "SCHEDULED",
            allowableValues = {"SCHEDULED", "COMPLETED", "CANCELED", "RATED"}
    )
    private Status status;

    // Enum for appointment status
    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELED,
        RATED
    }

    // No-argument constructor (required by JPA)
    public AppointmentEntity() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getPatient() {
        return patient;
    }

    public void setPatient(UserEntity patient) {
        this.patient = patient;
    }

    public UserEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(UserEntity doctor) {
        this.doctor = doctor;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
