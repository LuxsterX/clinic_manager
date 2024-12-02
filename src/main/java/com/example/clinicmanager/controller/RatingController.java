package com.example.clinicmanager.controller;

import com.example.clinicmanager.dto.RatingDTO;
import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.service.RatingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/ratings")
@Tag(name = "Ratings API", description = "API for managing ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @Operation(summary = "Rate an appointment", description = "Allows a patient to rate a completed appointment")
    @PostMapping("/rate")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<?> rateAppointment(
            @RequestBody RatingDTO ratingDTO, // Use @RequestBody to map the incoming JSON payload
            Principal principal) {
        try {
            RatingEntity rating = ratingService.rateAppointment(ratingDTO.getAppointmentId(), ratingDTO.getScore(), ratingDTO.getComments(), principal.getName());
            return ResponseEntity.ok(rating);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Appointment not found.");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An unexpected error occurred.");
        }
    }

}
