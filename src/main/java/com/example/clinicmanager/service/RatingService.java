package com.example.clinicmanager.service;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.RatingEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.RatingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final AppointmentRepository appointmentRepository;

    public RatingService(RatingRepository ratingRepository, AppointmentRepository appointmentRepository) {
        this.ratingRepository = ratingRepository;
        this.appointmentRepository = appointmentRepository;
    }

    public RatingEntity rateAppointment(Long appointmentId, int score) {
        Optional<AppointmentEntity> appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            throw new IllegalArgumentException("Appointment not found.");
        }

        AppointmentEntity appointment = appointmentOpt.get();

        if (appointment.getDoctorReport() == null) {
            throw new IllegalStateException("Cannot rate an appointment that has not been completed.");
        }

        RatingEntity rating = new RatingEntity();
        rating.setAppointment(appointment);
        rating.setDoctor(appointment.getDoctor());
        rating.setPatient(appointment.getPatient());
        rating.setScore(score);

        return ratingRepository.save(rating);
    }
}
