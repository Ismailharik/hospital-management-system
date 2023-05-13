package com.example.doctorbackend.controllers;

import com.example.doctorbackend.auth.RegisterRequest;
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

    @GetMapping
    @PreAuthorize("hasAuthority('admin:read')")
    public String get() {
        return "GET:: admin controller";
    }

    @PostMapping("/add_doctor")
    public ResponseEntity<String> addManager(@RequestBody RegisterRequest request) {
        request.setRole(Role.DOCTOR);
        this.adminService.addDoctor(request);
        return ResponseEntity.ok("Doctor user created successfully.");
    }
}
