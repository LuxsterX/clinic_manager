package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.AppointmentDTO;
import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public AppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));
    }

    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, String patientUsername) {
        AppointmentEntity appointment = mapToEntity(appointmentDTO);
        appointment.setPatient(userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> new NoSuchElementException("Patient not found")));
        return mapToDTO(appointmentRepository.save(appointment));
    }

    public void cancelAppointment(Long appointmentId, String patientUsername) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        if (!appointment.getPatient().getUsername().equals(patientUsername)) {
            throw new IllegalArgumentException("You are not authorized to cancel this appointment.");
        }

        appointmentRepository.delete(appointment);
    }

    private AppointmentDTO mapToDTO(AppointmentEntity entity) {
        return new AppointmentDTO(
                entity.getId(),
                entity.getPatient().getId(),
                entity.getDoctor().getId(),
                entity.getDateTime(),
                entity.getDetails()
        );
    }

    private AppointmentEntity mapToEntity(AppointmentDTO dto) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setDateTime(dto.getDateTime());
        entity.setDetails(dto.getDetails());
        return entity;
    }
}
