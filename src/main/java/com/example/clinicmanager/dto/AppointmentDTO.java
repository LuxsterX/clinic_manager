package com.example.clinicmanager.dto;

import java.time.LocalDateTime;

public class AppointmentDTO {

    private Long id;
    private Long patientId;
    private Long doctorId;
    private LocalDateTime dateTime;
    private String details;

    public AppointmentDTO() {}

    public AppointmentDTO(Long id, Long patientId, Long doctorId, LocalDateTime dateTime, String details) {
        this.id = id;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.dateTime = dateTime;
        this.details = details;
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
}
