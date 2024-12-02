package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.RatingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;

/**
 * Repository interface for managing {@link RatingEntity} entities.
 */
@Repository
@Tag(name = "Rating Repository", description = "Repository for managing ratings")
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    @Operation(summary = "Find all ratings given by a specific patient")
    List<RatingEntity> findByPatientId(Long patientId);

    @Operation(summary = "Find all ratings received by a specific doctor")
    List<RatingEntity> findByDoctorId(Long doctorId);

    @Operation(summary = "Find all ratings within a specified range")
    List<RatingEntity> findByRatingBetween(Integer minRating, Integer maxRating);

    @Operation(summary = "Find all ratings for a specific doctor with pagination")
    Page<RatingEntity> findByDoctorId(Long doctorId, Pageable pageable);

    @Operation(summary = "Find all ratings for a specific patient with pagination")
    Page<RatingEntity> findByPatientId(Long patientId, Pageable pageable);
}
