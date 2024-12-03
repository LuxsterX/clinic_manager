package com.example.clinicmanager.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Doctor details")
public class DoctorDTO {

    @Schema(description = "Unique identifier of the doctor", example = "1")
    private Long id;

    @Schema(description = "Full name of the doctor", example = "Dr. John Doe")
    private String fullName;

    public DoctorDTO(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
