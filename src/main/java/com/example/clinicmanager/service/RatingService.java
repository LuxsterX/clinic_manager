package com.example.clinicmanager.service;

import com.example.clinicmanager.exceptions.InvalidAppointmentStatusException;
import com.example.clinicmanager.exceptions.ResourceNotFoundException;
import com.example.clinicmanager.exceptions.UnauthorizedAccessException;
import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.RatingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Tag(name = "Rating Service", description = "Service for managing ratings and reviews for appointments.")
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AppointmentRepository appointmentRepository;

    public RatingService(RatingRepository ratingRepository, AppointmentRepository appointmentRepository) {
        this.ratingRepository = ratingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    @Operation(
            summary = "Rate an appointment",
            description = "Allows a patient to rate a completed appointment. The appointment must have a 'COMPLETED' status.",
            tags = {"Rating"}
    )
    public RatingEntity rateAppointment(Long appointmentId, int score, String comments, String patientUsername) {
        if (score < 1 || score > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5.");
        }

        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment not found with ID: " + appointmentId));

        validateAppointmentForRating(appointment, patientUsername);

        // Create and save a new rating
        RatingEntity rating = new RatingEntity();
        rating.setDoctor(appointment.getDoctor());
        rating.setPatient(appointment.getPatient());
        rating.setRating(score);
        rating.setComments(comments);

        RatingEntity savedRating = ratingRepository.save(rating);

        // Update the appointment status to "RATED"
        appointment.setStatus(AppointmentEntity.Status.RATED);
        appointmentRepository.save(appointment);

        return savedRating;
    }

    private void validateAppointmentForRating(AppointmentEntity appointment, String patientUsername) {
        if (appointment.getStatus() != AppointmentEntity.Status.COMPLETED) {
            throw new InvalidAppointmentStatusException("Only completed appointments can be rated.");
        }

        if (!appointment.getPatient().getUsername().equals(patientUsername)) {
            throw new UnauthorizedAccessException("You can only rate your own appointments.");
        }
    }
}
