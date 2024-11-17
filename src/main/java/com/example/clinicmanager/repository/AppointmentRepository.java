package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    // Wyszukiwanie wizyt pacjenta według statusu
    List<AppointmentEntity> findByPatientAndStatus(UserEntity patient, AppointmentEntity.Status status);

    // Wyszukiwanie wizyt lekarza według statusu
    List<AppointmentEntity> findByDoctorAndStatus(UserEntity doctor, AppointmentEntity.Status status);

    // Wyszukiwanie wizyt według daty i statusu
    List<AppointmentEntity> findByDoctorAndDateTimeBetweenAndStatus(UserEntity doctor, LocalDateTime start, LocalDateTime end, AppointmentEntity.Status status);
    List<AppointmentEntity> findByPatientAndDateTimeBetweenAndStatus(UserEntity patient, LocalDateTime start, LocalDateTime end, AppointmentEntity.Status status);
}
