package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.DoctorDTO;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Tag(name = "Doctor Service", description = "Service for managing doctor-related operations")
public class DoctorService {

    private final UserRepository userRepository;

    @Autowired
    public DoctorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Operation(
            summary = "Get all doctors",
            description = "Retrieve a list of all registered doctors with their ID and full name"
    )
    public List<DoctorDTO> getAllDoctors() {
        return userRepository.findByRole(UserEntity.Role.DOCTOR).stream()
                .map(user -> new DoctorDTO(user.getId(), user.getFullName()))
                .collect(Collectors.toList());
    }
}
