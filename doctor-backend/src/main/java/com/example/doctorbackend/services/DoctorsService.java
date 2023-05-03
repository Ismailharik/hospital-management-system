package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Doctor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

public interface DoctorsService {

    List<Doctor> getAllDoctors();

    Doctor getDoctorById(String id);

    Doctor createDoctor(Doctor doctor);

    Doctor updateDoctor(String id, Doctor doctor);

    void deleteDoctor(String id);
}
