package com.example.clinicmanager.controller;

import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.service.RatingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/{appointmentId}")
    public ResponseEntity<RatingEntity> rateAppointment(
            @PathVariable Long appointmentId,
            @RequestBody int score) {
        RatingEntity rating = ratingService.rateAppointment(appointmentId, score);
        return ResponseEntity.ok(rating);
    }
}
