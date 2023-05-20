package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Doctor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DoctorsService {

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(String id);

    Doctor createDoctor(Doctor doctor, MultipartFile file) throws IOException;

    Doctor updateDoctor(String id, Doctor doctor);

    void deleteDoctor(String id);
}
