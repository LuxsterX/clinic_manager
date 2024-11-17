package com.example.clinicmanager.repository;

import com.example.clinicmanager.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    // Znajdź użytkownika po nazwie użytkownika
    Optional<UserEntity> findByUsername(String username);

    Optional<Object> findByEmail(String email);
}
