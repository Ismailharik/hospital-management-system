package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.PatientDTO;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.mappers.Mapper;
import com.example.doctorbackend.repositories.PatientRepository;
import com.example.doctorbackend.user.Role;
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
    private  final Mapper mapper;

    @Override
    public List<PatientDTO> getAllPatients() {
        return patientsRepository.findByRole(Role.USER).stream().map(patient -> mapper.patientToPatientDto(patient)).toList();
    }

    @Override
    public PatientDTO getPatientById(String id) throws NotFoundException {
        Patient patient=patientsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient", "id", id));
        return mapper.patientToPatientDto(patient);
    }

    @Override
    public PatientDTO getPatientByEmail(String email) {
        Optional<Patient> patient = patientsRepository.findByEmail(email);
        if (patient.isEmpty()) {

            throw new NotFoundException("Patient", "email", email);
        }
        return mapper.patientToPatientDto(patient.get());
    }

    @Override
    public Patient addPatient(Patient patient, MultipartFile file) throws IOException {
        Optional<Patient> patientFound = patientsRepository.findByEmail(patient.getEmail());
        if (patientFound.isPresent()) {
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
    public Patient addPatient(Patient patient) {//add Patient without image
        Optional<Patient> patientFound = patientsRepository.findByEmail(patient.getEmail());
        if (patientFound.isPresent()) {
            throw new ConflictException("Email already exist, please login or try other one");
        }
        return patientsRepository.save(patient);
    }

    @Override
    public PatientDTO addImage(String id, MultipartFile file) throws IOException {
        Patient patient=patientsRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Patient", "id", id));


        String imagesLocation = System.getProperty("user.home") + "/hospital/images";
        File f = new File(imagesLocation);
        if (f.exists()) {
            System.out.println(" directory already exist");
        } else {
            System.out.println("create directories required to store image");
            f.mkdir();
        }

        String imageName = patient.getEmail() + ".jpg";// while users mail is unique so we will stored their images by their mails
        String imageSrc = imagesLocation + "/" + imageName;
        patient.setImage(imageSrc);
        Files.write(Paths.get(imageSrc), file.getBytes());// add image in server
        return mapper.patientToPatientDto(patientsRepository.save(patient));
    }

    @Override
    public PatientDTO updatePatient(String id, PatientDTO patientDTO) {
        Patient patient = patientsRepository.findById(id).orElseThrow(()->  new NotFoundException("Patient", "id",id));
        patient.setFirstname(patientDTO.getFirstname());
        patient.setPhone(patientDTO.getPhone());
        patient.setImage(patient.getImage());
        // he should not update his email
        // what if he updates his email to an existing email
        // then he will authenticate by other account
        // patient.setEmail(patientDetails.getEmail());


        return mapper.patientToPatientDto(patientsRepository.save(patient));
    }

    @Override
    public void deletePatient(String id) {
        Patient patient = patientsRepository.findById(id).orElseThrow(()->  new NotFoundException("Patient", "id",id));
        patientsRepository.delete(patient);
    }



}
