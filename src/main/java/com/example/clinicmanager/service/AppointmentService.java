package com.example.clinicmanager.service;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;

    public AppointmentService(AppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    // Wizyty pacjenta według statusu
    public List<AppointmentEntity> getAppointmentsForPatientByStatus(UserEntity patient, AppointmentEntity.Status status) {
        return appointmentRepository.findByPatientAndStatus(patient, status);
    }

    // Wizyty lekarza według statusu
    public List<AppointmentEntity> getAppointmentsForDoctorByStatus(UserEntity doctor, AppointmentEntity.Status status) {
        return appointmentRepository.findByDoctorAndStatus(doctor, status);
    }

    // Wizyty pacjenta według daty i statusu
    public List<AppointmentEntity> getAppointmentsForPatientByDateAndStatus(UserEntity patient, LocalDateTime start, LocalDateTime end, AppointmentEntity.Status status) {
        return appointmentRepository.findByPatientAndDateTimeBetweenAndStatus(patient, start, end, status);
    }

    // Wizyty lekarza według daty i statusu
    public List<AppointmentEntity> getAppointmentsForDoctorByDateAndStatus(UserEntity doctor, LocalDateTime start, LocalDateTime end, AppointmentEntity.Status status) {
        return appointmentRepository.findByDoctorAndDateTimeBetweenAndStatus(doctor, start, end, status);
    }
}
