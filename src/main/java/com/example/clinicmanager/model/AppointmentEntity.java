package com.example.clinicmanager.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
public class AppointmentEntity {

    public enum Status {
        SCHEDULED, COMPLETED, CANCELED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id")
    private UserEntity patient;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(name = "additional_notes")
    private String additionalNotes;

    @Column(name = "doctor_report")
    private String doctorReport;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;

    protected AppointmentEntity() {
    }

    public AppointmentEntity(UserEntity doctor, LocalDateTime dateTime, String additionalNotes, Status status) {
        this.doctor = doctor;
        this.dateTime = dateTime;
        this.additionalNotes = additionalNotes;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserEntity getDoctor() {
        return doctor;
    }

    public void setDoctor(UserEntity doctor) {
        this.doctor = doctor;
    }

    public UserEntity getPatient() {
        return patient;
    }

    public void setPatient(UserEntity patient) {
        this.patient = patient;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getAdditionalNotes() {
        return additionalNotes;
    }

    public void setAdditionalNotes(String additionalNotes) {
        this.additionalNotes = additionalNotes;
    }

    public String getDoctorReport() {
        return doctorReport;
    }

    public void setDoctorReport(String doctorReport) {
        this.doctorReport = doctorReport;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
