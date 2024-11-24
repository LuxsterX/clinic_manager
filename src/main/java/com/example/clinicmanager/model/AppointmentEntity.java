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

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    @Schema(description = "Patient linked to the appointment")
    private UserEntity patient;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    @Schema(description = "Doctor linked to the appointment")
    private UserEntity doctor;

    @Schema(description = "Date and time of the appointment", example = "2023-12-01T15:30:00")
    private LocalDateTime dateTime;

    @Schema(description = "Details or notes about the appointment", example = "Routine check-up")
    private String details;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Status of the appointment", example = "SCHEDULED")
    private Status status;

    // Enum for appointment status
    public enum Status {
        SCHEDULED,
        COMPLETED,
        CANCELED
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
