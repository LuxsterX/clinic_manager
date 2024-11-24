package com.example.clinicmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Data Transfer Object for Appointment")
public class AppointmentDTO {

    @Schema(description = "Unique identifier of the appointment", example = "1")
    private Long id;

    @Schema(description = "Unique identifier of the patient", example = "10")
    private Long patientId;

    @Schema(description = "Unique identifier of the doctor", example = "5")
    private Long doctorId;

    @Schema(description = "Date and time of the appointment", example = "2024-12-01T10:30:00")
    private LocalDateTime dateTime;

    @Schema(description = "Additional details or notes about the appointment", example = "Follow-up consultation")
    private String details;

    @Schema(description = "Status of the appointment", example = "Scheduled")
    private String status;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, Long patientId, Long doctorId, LocalDateTime dateTime, String details, String status) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.details = details;
        this.status = status;
    }

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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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
