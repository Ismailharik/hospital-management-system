package com.example.doctorbackend.controllers;

import com.example.doctorbackend.dto.PatientDTO;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.mappers.Mapper;
import com.example.doctorbackend.services.PatientsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
public class PatientsController {

    private final PatientsService patientsService;

    @GetMapping
    public ResponseEntity<List<PatientDTO>> getAllPatients() {
        List<PatientDTO> patients = patientsService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PatientDTO> getPatientById(@PathVariable String id) throws NotFoundException {
        PatientDTO patient = patientsService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @PostMapping("/add_image/{id}")
    public ResponseEntity<PatientDTO> addImage(
            @PathVariable String id,
            @RequestParam MultipartFile file
    ) throws NotFoundException, IOException {
        PatientDTO patient = patientsService.addImage(id, file);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<PatientDTO> getPatientByEmail(@Valid @Email @PathVariable String email) {
        PatientDTO patient = patientsService.getPatientByEmail(email);
        return ResponseEntity.ok(patient);
    }

//    @PostMapping
//    public ResponseEntity<PatientDTO> createPatient( @ModelAttribute PatientDTO patient,@RequestParam("file") MultipartFile file) throws IOException {
//        PatientDTO createdPatient = patientsService.addPatient(patient,file);
//        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
//    }

    @PutMapping("/{id}")
    public ResponseEntity<PatientDTO> updatePatient(@PathVariable String id, @Valid @RequestBody PatientDTO patient) throws NotFoundException {
        PatientDTO updatedPatient = patientsService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) throws NotFoundException {
        patientsService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }

}
