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

        // the methods below should return Patient as they won't be used by the controller
        Patient addPatient(Patient patient,MultipartFile file) throws IOException;
        Patient addPatient(Patient patient) ;
        PatientDTO addImage(String id,MultipartFile file) throws IOException;

        PatientDTO updatePatient(String id, PatientDTO patient) throws NotFoundException;

        void deletePatient(String id) throws NotFoundException;
}
