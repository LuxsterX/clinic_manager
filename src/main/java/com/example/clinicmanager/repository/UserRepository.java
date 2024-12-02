package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing {@link UserEntity} entities.
 */
@Repository
@Tag(name = "User Repository", description = "Repository for managing users")
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    @Operation(summary = "Find a user by username")
    Optional<UserEntity> findByUsername(String username);

    @Operation(summary = "Find a user by email")
    Optional<UserEntity> findByEmail(String email);

    @Operation(summary = "Find users by role")
    List<UserEntity> findByRole(UserEntity.Role role);

    @Operation(summary = "Find users by role with pagination")
    Page<UserEntity> findByRole(UserEntity.Role role, Pageable pageable);

    @Operation(summary = "Find a user by email ignoring case")
    Optional<UserEntity> findByEmailIgnoreCase(String email);

    @Operation(summary = "Find a user by username ignoring case")
    Optional<UserEntity> findByUsernameIgnoreCase(String username);
}
