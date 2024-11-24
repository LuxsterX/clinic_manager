package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Repository interface for managing {@link UserEntity} entities.
 */
@Repository
@Tag(name = "User Repository", description = "Repository for managing users")
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Find a user by username.
     *
     * @param username the username of the user
     * @return an {@link Optional} containing the user if found
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Find a user by email.
     *
     * @param email the email of the user
     * @return an {@link Optional} containing the user if found
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Find a ROLE users.
     *
     * @param role the email of the user
     * @return an {@link Optional} containing the user if found
     */
    List<UserEntity> findByRole(UserEntity.Role role);
}
