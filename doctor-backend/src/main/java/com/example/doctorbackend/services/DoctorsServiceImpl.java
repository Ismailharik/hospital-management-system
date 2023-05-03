package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor

public class DoctorsServiceImpl implements DoctorsService {

    private final DoctorRepository doctorRepository;


    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    @Override
    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
    }

    @Override
    public Doctor createDoctor(Doctor doctor) {
        if (doctorRepository.findByEmail(doctor .getEmail())!=null){
            throw new ConflictException("Email already exist, please login or try other one");
        }
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor updateDoctor(String id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        existingDoctor.setName(doctor.getName());
        existingDoctor.setSpeciality(doctor.getSpeciality());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setReview(doctor.getReview());
        existingDoctor.setTotalScore(doctor.getTotalScore());
        existingDoctor.setSatisfaction(doctor.getSatisfaction());
        existingDoctor.setImage(doctor.getImage());
        return doctorRepository.save(existingDoctor);
    }

    @Override
    public void deleteDoctor(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        doctorRepository.delete(doctor);
    }
}
