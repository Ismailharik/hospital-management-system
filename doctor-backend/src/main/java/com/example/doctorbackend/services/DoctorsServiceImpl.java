package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@AllArgsConstructor

public class DoctorsServiceImpl implements DoctorsService {

    private final DoctorRepository doctorRepository;


    @Override
    public List<Doctor> getAllDoctors() {
        return doctorRepository.findByRole(Role.DOCTOR);
    }

    @Override
    public Doctor getDoctorById(String id) {
        return doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
    }

    @Override
    public Doctor createDoctor(Doctor doctor, MultipartFile file) throws IOException {

        if (doctorRepository.findByEmail(doctor .getEmail()).isPresent()) {
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
        String imageName = doctor.getEmail() + ".jpg";// while users mail is unique so we will stored their images by their mails
        String imageSrc = imagesLocation + "/" + imageName;
        doctor.setImage(imageSrc);
        Files.write(Paths.get(imageSrc), file.getBytes());// add image in server
        return doctorRepository.save(doctor);
    }


    @Override
    public Doctor updateDoctor(String id, Doctor doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        existingDoctor.setFirstname(doctor.getFirstname());
        existingDoctor.setComment(doctor.getComment());
        existingDoctor.setPhone(doctor.getPhone());
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
