package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@AllArgsConstructor
@Service
@Validated
public class PatientsServiceImpl implements PatientsService {

    private final PatientRepository patientsRepository;

    @Override
    public List<Patient> getAllPatients() {
        return patientsRepository.findAll();
    }

    @Override
    public Patient getPatientById(String id) throws NotFoundException {
        return patientsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient not found with id: " + id));
    }

    @Override
    public Patient getPatientByEmail(String email) {
        Patient patient= patientsRepository.findByEmail(email);
        if (patient==null){
            throw new NotFoundException("email : "+email+" is not found");
        }
        return patient;
    }

    @Override
    public Patient addPatient(Patient patient) {

        if (patientsRepository.findByEmail(patient.getEmail())!=null){
            throw new ConflictException("Email already exist, please login or try other one");
        }
        return patientsRepository.save(patient);
    }

    @Override
    public Patient updatePatient(String id, Patient patientDetails) throws NotFoundException {
        Patient patient = getPatientById(id);
        patient.setName(patientDetails.getName());

        patient.setPhone(patientDetails.getPhone());
        patient.setEmail(patientDetails.getEmail());

        return patientsRepository.save(patient);
    }

    @Override
    public void deletePatient(String id) throws NotFoundException {
        Patient patient = getPatientById(id);
        patientsRepository.delete(patient);
    }
}
