package com.example.clinicmanager.model;

import jakarta.persistence.*;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Entity class representing a rating given by a patient to a doctor.
 */
@Entity
@Table(name = "ratings")
@Schema(description = "Entity representing a rating in the clinic system")
public class RatingEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique identifier of the rating", example = "1")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    @Schema(description = "Patient who gave the rating")
    private UserEntity patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    @Schema(description = "Doctor who received the rating")
    private UserEntity doctor;

    @Column(nullable = false)
    @Schema(description = "Rating value between 1 and 5", example = "5", allowableValues = {"1", "2", "3", "4", "5"})
    private Integer rating;

    @Column(length = 255)
    @Schema(description = "Optional comments provided by the patient", example = "Excellent service!")
    private String comments;

    // Default constructor for JPA
    public RatingEntity() {
    }

    // Getters and Setters
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
