package com.example.clinicmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object for submitting a rating")
public class RatingDTO {

    @Schema(description = "ID of the appointment being rated", example = "123")
    private Long appointmentId;

    @Schema(description = "Score for the rating, from 1 to 5", example = "5")
    private int score;

    @Schema(description = "Optional comments for the rating", example = "Great service!")
    private String comments;

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
