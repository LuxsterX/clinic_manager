package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.RatingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {
}
