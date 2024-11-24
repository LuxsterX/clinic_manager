package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for managing {@link AppointmentEntity} entities.
 */
@Repository
@Tag(name = "Appointment Repository", description = "Repository for managing appointments")
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    /**
     * Finds all appointments for a specific patient by their ID.
     *
     * @param patientId the ID of the patient
     * @return a list of appointments associated with the given patient ID
     */
    List<AppointmentEntity> findByPatientId(Long patientId);

    /**
     * Finds all appointments for a specific doctor by their ID.
     *
     * @param doctorId the ID of the doctor
     * @return a list of appointments associated with the given doctor ID
     */
    List<AppointmentEntity> findByDoctorId(Long doctorId);

    /**
     * Finds all appointments for a specific patient by their ID and status.
     *
     * @param patientId the ID of the patient
     * @param status the status of the appointment
     * @return a list of appointments for the patient filtered by status
     */
    List<AppointmentEntity> findByPatientIdAndStatus(Long patientId, AppointmentEntity.Status status);

    /**
     * Finds all appointments for a specific doctor by their ID and status.
     *
     * @param doctorId the ID of the doctor
     * @param status the status of the appointment
     * @return a list of appointments for the doctor filtered by status
     */
    List<AppointmentEntity> findByDoctorIdAndStatus(Long doctorId, AppointmentEntity.Status status);

    /**
     * Finds all appointments scheduled within a given date range for a specific doctor.
     *
     * @param doctorId the ID of the doctor
     * @param start the start of the date range
     * @param end the end of the date range
     * @return a list of appointments for the doctor within the specified date range
     */
    List<AppointmentEntity> findByDoctorIdAndDateTimeBetween(Long doctorId, LocalDateTime start, LocalDateTime end);

    /**
     * Finds appointments where the user is either the patient or the doctor.
     *
     * @param user the user (either patient or doctor)
     * @return a list of appointments associated with the user
     */
    @Query("SELECT a FROM AppointmentEntity a WHERE a.patient = :user OR a.doctor = :user")
    List<AppointmentEntity> findByPatientOrDoctor(@Param("user") UserEntity user);
}
