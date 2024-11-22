package com.example.clinicmanager.controller;

import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Ratings API", description = "API for managing ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/rate")
    @Operation(summary = "Rate an appointment", description = "Allows users to rate an appointment with a score and comments")
    public ResponseEntity<RatingEntity> rateAppointment(
            @Parameter(description = "ID of the appointment to rate", required = true) @RequestParam Long appointmentId,
            @Parameter(description = "Score for the appointment", required = true) @RequestParam int score,
            @Parameter(description = "Additional comments about the appointment", required = false) @RequestParam String comments) {
        RatingEntity rating = ratingService.rateAppointment(appointmentId, score, comments);
        return ResponseEntity.ok(rating);
    }
}
