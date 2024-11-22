package com.example.clinicmanager.service;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AppointmentRepository appointmentRepository;

    public RatingService(RatingRepository ratingRepository, AppointmentRepository appointmentRepository) {
        this.ratingRepository = ratingRepository;
        this.appointmentRepository = appointmentRepository;
    }

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
