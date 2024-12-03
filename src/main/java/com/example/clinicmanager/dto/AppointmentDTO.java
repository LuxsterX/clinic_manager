package com.example.clinicmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Schema(description = "Data Transfer Object for Appointment")
public class AppointmentDTO {

    @Schema(description = "Unique identifier of the appointment", example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the patient", example = "10")
    @NotNull(message = "Patient ID is required")
    private Long patientId;

    @Schema(description = "Full name of the patient", example = "John Doe")
    private String patientName;

    @Schema(description = "Unique identifier of the doctor", example = "5")
    @NotNull(message = "Doctor ID is required")
    private Long doctorId;

    @Schema(description = "Full name of the doctor", example = "Dr. Jane Smith")
    private String doctorName;

    @Schema(description = "Date and time of the appointment", example = "2024-12-01T10:30:00")
    @NotNull(message = "Date and time are required")
    private LocalDateTime dateTime;

    @Schema(description = "Additional details or notes about the appointment", example = "Follow-up consultation")
    private String details;

    @Schema(
            description = "Status of the appointment",
            example = "Scheduled",
            allowableValues = {"Scheduled", "Completed", "Canceled"}
    )
    private String status;

    // Bezargumentowy konstruktor
    public AppointmentDTO() {
    }

    // Konstruktor z polami
    public AppointmentDTO(Long id, Long patientId, String patientName, Long doctorId, String doctorName, LocalDateTime dateTime, String details, String status) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.dateTime = dateTime;
        this.details = details;
        this.status = status;
    }

    // Gettery i Settery
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
