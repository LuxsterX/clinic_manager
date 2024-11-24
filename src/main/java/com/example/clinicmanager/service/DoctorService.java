package com.example.clinicmanager.service;

import com.example.clinicmanager.dto.DoctorDTO;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final UserRepository userRepository;

    @Autowired
    public DoctorService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<DoctorDTO> getAllDoctors() {
        return userRepository.findByRole(UserEntity.Role.DOCTOR).stream()
                .map(user -> new DoctorDTO(user.getId(), user.getFullName()))
                .collect(Collectors.toList());
    }
}
