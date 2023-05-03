package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.NotFoundException;

import java.util.List;
public interface PatientsService {

        List<Patient> getAllPatients();

        Patient getPatientById(String id) throws NotFoundException;

        Patient getPatientByEmail(String email);

        Patient addPatient(Patient patient);

        Patient updatePatient(String id, Patient patient) throws NotFoundException;

        void deletePatient(String id) throws NotFoundException;
}
