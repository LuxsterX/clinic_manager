package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link AppointmentEntity} entities.
 */
@Repository
@Tag(name = "Appointment Repository", description = "Repository for managing appointments")
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Operation(summary = "Find all appointments for a specific patient by their ID")
    List<AppointmentEntity> findByPatientId(Long patientId);

    @Operation(summary = "Find all appointments for a specific doctor by their ID")
    List<AppointmentEntity> findByDoctorId(Long doctorId);

    @Operation(summary = "Find all appointments for a specific patient by their ID and status")
    List<AppointmentEntity> findByPatientIdAndStatus(Long patientId, AppointmentEntity.Status status);

    @Operation(summary = "Find all appointments for a specific doctor by their ID and status")
    List<AppointmentEntity> findByDoctorIdAndStatus(Long doctorId, AppointmentEntity.Status status);

    @Operation(summary = "Find all appointments scheduled within a given date range for a specific doctor")
    List<AppointmentEntity> findByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    @Operation(summary = "Find appointments where the user is either the patient or the doctor")
    @Query("SELECT a FROM AppointmentEntity a WHERE a.patient = :user OR a.doctor = :user")
    List<AppointmentEntity> findByPatientOrDoctor(@Param("user") UserEntity user);

    @Operation(summary = "Find all future appointments for a specific doctor sorted by date")
    List<AppointmentEntity> findByDoctorIdAndDateTimeAfterOrderByDateTimeAsc(Long doctorId, LocalDateTime now);

    @Operation(summary = "Find all appointments for a specific patient with pagination")
    Page<AppointmentEntity> findByPatientId(Long patientId, Pageable pageable);

    @Operation(summary = "Find all appointments for a specific doctor with eager loading")
    @EntityGraph(attributePaths = {"patient", "doctor"})
    List<AppointmentEntity> findAllByDoctorId(Long doctorId);
}
