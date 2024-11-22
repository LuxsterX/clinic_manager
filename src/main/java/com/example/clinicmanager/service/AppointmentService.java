package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.AppointmentDTO;
import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

/**
 * Service layer for managing appointments.
 */
@Service
@Tag(name = "Appointment Service", description = "Service for managing appointments")
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;

    @Autowired
    public AppointmentService(AppointmentRepository appointmentRepository, UserRepository userRepository) {
        this.appointmentRepository = appointmentRepository;
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all appointments.
     *
     * @return a list of all appointments as DTOs
     */
    @Operation(summary = "Get all appointments", description = "Fetches a list of all appointments.")
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    /**
     * Retrieves a specific appointment by ID.
     *
     * @param id the ID of the appointment
     * @return the appointment as a DTO
     */
    @Operation(summary = "Get appointment by ID", description = "Fetches details of a specific appointment using its ID.")
    public AppointmentDTO getAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(this::mapToDTO)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));
    }

    /**
     * Creates a new appointment for a specific patient.
     *
     * @param appointmentDTO the appointment data
     * @param patientUsername the username of the patient
     * @return the created appointment as a DTO
     */
    @Operation(summary = "Create an appointment", description = "Creates a new appointment for the given patient.")
    public AppointmentDTO createAppointment(AppointmentDTO appointmentDTO, String patientUsername) {
        AppointmentEntity appointment = mapToEntity(appointmentDTO);
        appointment.setPatient(userRepository.findByUsername(patientUsername)
                .orElseThrow(() -> new NoSuchElementException("Patient not found")));
        return mapToDTO(appointmentRepository.save(appointment));
    }

    /**
     * Cancels an appointment.
     *
     * @param appointmentId the ID of the appointment to cancel
     * @param patientUsername the username of the patient canceling the appointment
     */
    @Operation(summary = "Cancel an appointment", description = "Allows a patient to cancel their own appointment.")
    public void cancelAppointment(Long appointmentId, String patientUsername) {
        AppointmentEntity appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new NoSuchElementException("Appointment not found"));

        if (!appointment.getPatient().getUsername().equals(patientUsername)) {
            throw new IllegalArgumentException("You are not authorized to cancel this appointment.");
        }

        appointmentRepository.delete(appointment);
    }

    /**
     * Maps an AppointmentEntity to an AppointmentDTO.
     *
     * @param entity the appointment entity
     * @return the appointment DTO
     */
    private AppointmentDTO mapToDTO(AppointmentEntity entity) {
        return new AppointmentDTO(
                entity.getId(),
                entity.getPatient().getId(),
                entity.getDoctor().getId(),
                entity.getDateTime(),
                entity.getDetails()
        );
    }

    /**
     * Maps an AppointmentDTO to an AppointmentEntity.
     *
     * @param dto the appointment DTO
     * @return the appointment entity
     */
    private AppointmentEntity mapToEntity(AppointmentDTO dto) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setDateTime(dto.getDateTime());
        entity.setDetails(dto.getDetails());
        return entity;
    }
}
