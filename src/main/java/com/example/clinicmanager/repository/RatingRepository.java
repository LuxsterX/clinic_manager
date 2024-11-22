package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Repository interface for managing {@link RatingEntity} entities.
 */
@Repository
@Tag(name = "Rating Repository", description = "Repository for managing ratings")
public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
    // Additional query methods can be added here as needed
}
