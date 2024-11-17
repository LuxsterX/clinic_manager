package com.example.clinicmanager.controller;

import com.example.clinicmanager.model.AppointmentEntity;
import com.example.clinicmanager.model.UserEntity;
import com.example.clinicmanager.service.AppointmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @GetMapping
    public ResponseEntity<List<AppointmentEntity>> getAppointments(
            @AuthenticationPrincipal UserDetails currentUser,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate,
            @RequestParam(required = false) String status,
            @RequestParam("role") String role) {

        UserEntity user = (UserEntity) currentUser;

        LocalDateTime start = startDate != null ? LocalDateTime.parse(startDate) : LocalDateTime.MIN;
        LocalDateTime end = endDate != null ? LocalDateTime.parse(endDate) : LocalDateTime.MAX;
        AppointmentEntity.Status appointmentStatus = status != null ? AppointmentEntity.Status.valueOf(status.toUpperCase()) : null;

        if (role.equalsIgnoreCase("PATIENT")) {
            if (appointmentStatus != null) {
                return ResponseEntity.ok(appointmentService.getAppointmentsForPatientByDateAndStatus(user, start, end, appointmentStatus));
            }
            return ResponseEntity.ok(appointmentService.getAppointmentsForPatientByDateAndStatus(user, start, end, AppointmentEntity.Status.SCHEDULED));
        } else if (role.equalsIgnoreCase("DOCTOR")) {
            if (appointmentStatus != null) {
                return ResponseEntity.ok(appointmentService.getAppointmentsForDoctorByDateAndStatus(user, start, end, appointmentStatus));
            }
            return ResponseEntity.ok(appointmentService.getAppointmentsForDoctorByDateAndStatus(user, start, end, AppointmentEntity.Status.SCHEDULED));
        }

        return ResponseEntity.badRequest().build();
    }
}
