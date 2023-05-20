package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

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
                .orElseThrow(() -> new NotFoundException("Patient", "id", id));
    }

    @Override
    public Patient getPatientByEmail(String email) {
        Optional<Patient> patient = patientsRepository.findByEmail(email);
        if (patient.isEmpty()) {

            throw new NotFoundException("Patient", "email", email);
        }
        return patient.get();
    }

    @Override
    public Patient addPatient(Patient patient, MultipartFile file) throws IOException {

        if (patientsRepository.findByEmail(patient.getEmail()).isPresent()) {
            throw new ConflictException("Email already exist, please login or try other one");
        }
        String imagesLocation = System.getProperty("user.home") + "/hospital/images";
        File f = new File(imagesLocation);
        if (f.exists()) {
            System.out.println("stories directory already exist");
        } else {
            System.out.println("create stories directory");
            f.mkdir();
        }
        String imageName = patient.getEmail() + ".jpg";// while users mail is unique so we will stored their images by their mails
        String imageSrc = imagesLocation + "/" + imageName;
        patient.setImage(imageSrc);
        Files.write(Paths.get(imageSrc), file.getBytes());// add image in server
        return patientsRepository.save(patient);
    }

    @Override
    public Patient updatePatient(String id, Patient patientDetails) {
        Patient patient = getPatientById(id);
        patient.setFirstname(patientDetails.getFirstname());
        patient.setPhone(patientDetails.getPhone());

        // he should not update his email
        // what if he updates his email to an existing email
        // then he will authenticate by other account
        // patient.setEmail(patientDetails.getEmail());


        return patientsRepository.save(patient);
    }

    @Override
    public void deletePatient(String id) {
        Patient patient = getPatientById(id);
        if (patient == null) {
            throw new NotFoundException("Patient", "id", id);
        }
        patientsRepository.delete(patient);
    }



}
