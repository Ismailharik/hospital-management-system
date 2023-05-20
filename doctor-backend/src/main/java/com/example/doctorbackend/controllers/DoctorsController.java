package com.example.doctorbackend.controllers;

import com.example.doctorbackend.dto.DoctorDTO;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.services.DoctorsService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@AllArgsConstructor
public class DoctorsController {

    private final DoctorsService doctorService;


    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctorsDTO = doctorService.getAllDoctors();
        return new ResponseEntity<>(doctorsDTO, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable("id") String id) {
        DoctorDTO doctor = doctorService.getDoctorById(id);
        if (doctor == null) {
            throw new NotFoundException("Doctor not found with id: " + id);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }
    @PostMapping("/add_image/{id}")
    public ResponseEntity<DoctorDTO> addImage(@PathVariable("id") String id, @RequestParam MultipartFile file) throws IOException {
        DoctorDTO doctor = doctorService.addImage(id,file);
        if (doctor == null) {
            throw new NotFoundException("Doctor not found with id: " + id);
        }
        return new ResponseEntity<>(doctor, HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable("id") String id, @RequestBody DoctorDTO doctor) {
        DoctorDTO updatedDoctorDTO = doctorService.updateDoctor(id, doctor);
        if (updatedDoctorDTO == null) {
            throw new NotFoundException("Doctor not found with id: " + id);
        }
        return new ResponseEntity<>(updatedDoctorDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable("id") String id) {
        doctorService.deleteDoctor(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
