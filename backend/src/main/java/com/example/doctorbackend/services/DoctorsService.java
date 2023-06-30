package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.DoctorDTO;
import com.example.doctorbackend.entities.Doctor;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DoctorsService {

    List<DoctorDTO> getAllDoctors();

    DoctorDTO getDoctorById(String id);
    // this method will be used by register from admin endpoint

    Doctor createDoctor(Doctor doctor, MultipartFile file) throws IOException;
    Doctor createDoctor(Doctor doctor) ;

    DoctorDTO updateDoctor(String id, DoctorDTO doctorDTO);

    void deleteDoctor(String id);

    DoctorDTO addImage(String id, MultipartFile file) throws IOException;
}
