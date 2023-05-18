package com.example.doctorbackend.controllers;

import com.example.doctorbackend.auth.RegisterRequest;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.services.AdminService;
import com.example.doctorbackend.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;



    @PostMapping("/add_doctor/{categoryId}")
    public ResponseEntity<String> addDoctor(
            @RequestBody RegisterRequest request,
            @PathVariable String categoryId
    ) {
        request.setRole(Role.DOCTOR);

        adminService.addDoctor(request,categoryId);

        return ResponseEntity.ok("Doctor user created successfully.");
    }
}
