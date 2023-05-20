package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.services.DoctorsService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@AllArgsConstructor
public class DoctorsController {

    private final DoctorsService doctorService;


    @GetMapping
    public ResponseEntity<List<Doctor>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctors, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Doctor> getDoctorById(@PathVariable("id") String id) {
        Doctor doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            throw new NotFoundException("Doctor not found with id: " + id);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }

//    @PostMapping
//    public ResponseEntity<Doctor> createDoctor(@Valid @ModelAttribute Doctor doctor,
//                                               @RequestParam MultipartFile file) {
//        Doctor createdDoctor = doctorService.createDoctor(doctor);
//        return new ResponseEntity<>(createdDoctor, HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<Doctor> updateDoctor(@PathVariable("id") String id, @RequestBody Doctor doctor) {
        Doctor updatedDoctor = doctorService.updateDoctor(id, doctor);
        if (updatedDoctor == null) {
            throw new NotFoundException("Doctor not found with id: " + id);
        }
        return new ResponseEntity<>(updatedDoctor, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") String id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
