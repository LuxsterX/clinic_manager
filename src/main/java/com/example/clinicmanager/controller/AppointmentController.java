package com.example.clinicmanager.controller;

import com.example.clinicmanager.dto.AppointmentDTO;
import com.example.clinicmanager.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/api/appointments")
@Tag(name = "Appointments", description = "APIs for managing appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Autowired
    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Operation(summary = "Get all appointments", description = "Retrieve a list of all appointments")
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }

    @Operation(summary = "Get an appointment by ID", description = "Retrieve details of a specific appointment by its ID")
    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long appointmentId) {
        return ResponseEntity.ok(appointmentService.getAppointmentById(appointmentId));
    }

    @Operation(summary = "Create a new appointment", description = "Create a new appointment for a patient")
    @PostMapping
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO, Principal principal) {
        return ResponseEntity.ok(appointmentService.createAppointment(appointmentDTO, principal.getName()));
    }

    @Operation(summary = "Cancel an appointment", description = "Cancel an existing appointment for a patient")
    @DeleteMapping("/{appointmentId}")
    @PreAuthorize("hasRole('PATIENT')")
    public ResponseEntity<String> cancelAppointment(@PathVariable Long appointmentId, Principal principal) {
        try {
            appointmentService.cancelAppointment(appointmentId, principal.getName());
            return ResponseEntity.ok("Appointment canceled successfully.");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(404).body("Appointment not found.");
        }
    }
}
