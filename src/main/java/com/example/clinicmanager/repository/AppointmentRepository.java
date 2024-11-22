package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.tags.Tag;

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
}
