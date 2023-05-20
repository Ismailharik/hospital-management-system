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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@AllArgsConstructor
public class AdminController {
    private final AdminService adminService;



    @PostMapping("/add_doctor/{categoryId}")
    public ResponseEntity<String> addDoctor(
            @ModelAttribute RegisterRequest request,
            @RequestParam("file") MultipartFile file,
            @PathVariable String categoryId
    ) throws IOException {
        request.setRole(Role.DOCTOR);
        System.out.println();
        System.out.println("--------------------");
        System.out.println(request.toString());
        adminService.addDoctor(request,categoryId,file);

        return ResponseEntity.ok("Doctor user created successfully.");
    }
    @PostMapping("/add_doctor/v2/{categoryId}")
    public ResponseEntity<String> addDoctor(
            @RequestBody RegisterRequest request,
            @PathVariable String categoryId
    ) throws IOException {
        request.setRole(Role.DOCTOR);
        adminService.addDoctor(request,categoryId,null);

        return ResponseEntity.ok("Doctor user created successfully.");
    }
}
