package com.example.clinicmanager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "ratings")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int score;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private UserEntity doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private UserEntity patient;

    @OneToOne
    @JoinColumn(name = "appointment_id", nullable = false)
    private AppointmentEntity appointment;

    // Default constructor for JPA
    public RatingEntity() {
    }

    // Constructor with fields
    public RatingEntity(int score, UserEntity doctor, UserEntity patient, AppointmentEntity appointment) {
        this.score = score;
        this.doctor = doctor;
        this.patient = patient;
        this.appointment = appointment;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public AppointmentEntity getAppointment() {
        return appointment;
    }

    public void setAppointment(AppointmentEntity appointment) {
        this.appointment = appointment;
    }
}
