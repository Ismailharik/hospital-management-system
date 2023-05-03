package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.services.PatientsService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/v1/patients")
@AllArgsConstructor
public class PatientsController {

    private final PatientsService patientsService;

    @GetMapping
    public ResponseEntity<List<Patient>> getAllPatients() {
        List<Patient> patients = patientsService.getAllPatients();
        return new ResponseEntity<>(patients, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(@PathVariable String id) throws NotFoundException {
        Patient patient = patientsService.getPatientById(id);
        return ResponseEntity.ok(patient);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<Patient> getPatientByEmail(@Valid @Email @PathVariable String email) {
        Patient patient = patientsService.getPatientByEmail(email);
            return ResponseEntity.ok(patient);
    }

    @PostMapping
    public ResponseEntity<Patient> createPatient( @RequestBody Patient patient) {
        Patient createdPatient = patientsService.addPatient(patient);
        return new ResponseEntity<>(createdPatient, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(@PathVariable String id,@Valid @RequestBody Patient patient) throws NotFoundException {
        Patient updatedPatient = patientsService.updatePatient(id, patient);
        return ResponseEntity.ok(updatedPatient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(@PathVariable String id) throws NotFoundException {
        patientsService.deletePatient(id);
        return ResponseEntity.noContent().build();
    }
}
