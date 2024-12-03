package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.AppointmentDTO;
import com.example.clinicmanager.exceptions.ResourceNotFoundException;
import com.example.clinicmanager.exceptions.UnauthorizedAccessException;
import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.AppointmentRepository;
import com.example.clinicmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
        UserEntity user = findUserByUsername(username);

        return appointmentRepository.findByPatientOrDoctor(user).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public AppointmentDTO createAppointmentAsPatient(AppointmentDTO appointmentDTO, String patientUsername) {
        AppointmentEntity appointment = mapToEntity(appointmentDTO);

        appointment.setPatient(findUserByUsername(patientUsername));
        appointment.setDoctor(findUserById(appointmentDTO.getDoctorId()));

        // Set default status if not provided
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentEntity.Status.SCHEDULED);
        }

        return mapToDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    public AppointmentDTO createAppointmentAsDoctor(AppointmentDTO appointmentDTO, String doctorUsername) {
        AppointmentEntity appointment = mapToEntity(appointmentDTO);

        appointment.setDoctor(findUserByUsername(doctorUsername));
        appointment.setPatient(findUserById(appointmentDTO.getPatientId()));

        // Set default status if not provided
        if (appointment.getStatus() == null) {
            appointment.setStatus(AppointmentEntity.Status.SCHEDULED);
        }

        return mapToDTO(appointmentRepository.save(appointment));
    }

    @Transactional
    public void markAppointmentAsCanceled(Long appointmentId, String patientUsername) {
        AppointmentEntity appointment = findAppointmentById(appointmentId);

        if (!appointment.getPatient().getUsername().equals(patientUsername)) {
            throw new UnauthorizedAccessException("You are not authorized to cancel this appointment.");
        }

        appointment.setStatus(AppointmentEntity.Status.CANCELED);
        appointmentRepository.save(appointment);
    }

    @Transactional
    public void markAppointmentAsCompleted(Long appointmentId, String doctorUsername) {
        AppointmentEntity appointment = findAppointmentById(appointmentId);

        if (!appointment.getDoctor().getUsername().equals(doctorUsername)) {
            throw new UnauthorizedAccessException("You are not authorized to complete this appointment.");
        }

        if (!appointment.getStatus().equals(AppointmentEntity.Status.SCHEDULED)) {
            throw new IllegalStateException("Only scheduled appointments can be marked as completed.");
        }

        appointment.setStatus(AppointmentEntity.Status.COMPLETED);
        appointmentRepository.save(appointment);
    }

    public List<AppointmentDTO> getAppointmentsForUserByStatus(String username, AppointmentEntity.Status status) {
        UserEntity user = findUserByUsername(username);

        if (user.getRole().equals(UserEntity.Role.PATIENT)) {
            return appointmentRepository.findByPatientIdAndStatus(user.getId(), status).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        if (user.getRole().equals(UserEntity.Role.DOCTOR)) {
            return appointmentRepository.findByDoctorIdAndStatus(user.getId(), status).stream()
                    .map(this::mapToDTO)
                    .collect(Collectors.toList());
        }

        throw new IllegalArgumentException("Invalid user role.");
    }

    private UserEntity findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User with username " + username + " not found."));
    }

    private UserEntity findUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));
    }

    private AppointmentEntity findAppointmentById(Long appointmentId) {
        return appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Appointment with ID " + appointmentId + " not found."));
    }

    private AppointmentDTO mapToDTO(AppointmentEntity entity) {
        return new AppointmentDTO(
                entity.getId(),
                entity.getPatient().getId(),
                entity.getPatient().getFullName(),
                entity.getDoctor().getId(),
                entity.getDoctor().getFullName(),
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
