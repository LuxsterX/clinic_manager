package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.AppointmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {
    List<AppointmentEntity> findByPatientId(Long patientId);

    List<AppointmentEntity> findByDoctorId(Long doctorId);
}
