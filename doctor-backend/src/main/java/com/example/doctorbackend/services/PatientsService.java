package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.PatientDTO;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.NotFoundException;
import org.springframework.security.core.parameters.P;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
public interface PatientsService {

        List<PatientDTO> getAllPatients();

        PatientDTO getPatientById(String id) throws NotFoundException;

        PatientDTO getPatientByEmail(String email);

        Patient addPatient(Patient patient,MultipartFile file) throws IOException;
        Patient addPatient(Patient patient) ;

        PatientDTO updatePatient(String id, PatientDTO patient) throws NotFoundException;

        void deletePatient(String id) throws NotFoundException;
}
