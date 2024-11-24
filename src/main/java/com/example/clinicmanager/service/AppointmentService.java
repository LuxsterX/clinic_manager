package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.AppointmentDTO;
import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<AppointmentDTO> getAppointmentsForUser(String username) {
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        return appointmentRepository.findByPatientOrDoctor(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentDTO createAppointmentAsPatient(AppointmentDTO appointmentDTO, String patientUsername) {
        AppointmentEntity appointment = mapToEntity(appointmentDTO);

        appointment.setPatient(userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> new NoSuchElementException("Patient not found")));
        appointment.setDoctor(userRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new NoSuchElementException("Doctor not found")));

        // Set the default status if not provided
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentEntity.Status.SCHEDULED);
        }

        return mapToDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    public void markAppointmentAsCanceled(Long appointmentId, String patientUsername) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        if (!appointment.getPatient().getUsername().equals(patientUsername)) {
            throw new IllegalArgumentException("Unauthorized to cancel this appointment.");
        }

        // Update the status to CANCELED
        appointment.setStatus(AppointmentEntity.Status.CANCELED);
        appointmentRepository.save(appointment);
    }

    private AppointmentDTO mapToDTO(AppointmentEntity entity) {
        return new AppointmentDTO(
                entity.getId(),
                entity.getPatient().getId(),
                entity.getDoctor().getId(),
                entity.getDateTime(),
                entity.getDetails(),
                entity.getStatus().toString()
        );
    }

    private AppointmentEntity mapToEntity(AppointmentDTO dto) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setDateTime(dto.getDateTime());
        entity.setDetails(dto.getDetails());
        return entity;
    }
}
