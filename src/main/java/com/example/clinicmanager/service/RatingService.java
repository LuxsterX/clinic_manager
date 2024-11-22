package com.example.clinicmanager.service;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.RatingRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Service;

/**
 * Service for managing ratings for appointments.
 */
@Service
@Tag(name = "Rating Service", description = "Service for managing ratings and reviews for appointments.")
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AppointmentRepository appointmentRepository;

    public RatingService(RatingRepository ratingRepository, AppointmentRepository appointmentRepository) {
        this.ratingRepository = ratingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    /**
     * Rates an appointment by a patient.
     *
     * @param appointmentId the ID of the appointment to be rated
     * @param score the rating score
     * @param comments optional comments from the patient
     * @return the created RatingEntity object
     */
    @Operation(
            summary = "Rate an appointment",
            description = "Allows a patient to rate a completed appointment. The appointment must have a 'COMPLETED' status."
    )
    public RatingEntity rateAppointment(Long appointmentId, int score, String comments) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Appointment not found"));

        if (!"COMPLETED".equals(appointment.getStatus())) {
            throw new IllegalStateException("Cannot rate an appointment that is not completed.");
        }

        RatingEntity rating = new RatingEntity();
        rating.setDoctor(appointment.getDoctor());
        rating.setPatient(appointment.getPatient());
        rating.setRating(score);
        rating.setComments(comments);

        return ratingRepository.save(rating);
    }
}
