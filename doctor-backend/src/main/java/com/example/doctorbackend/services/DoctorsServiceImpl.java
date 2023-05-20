package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.DoctorDTO;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.mappers.Mapper;
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
import java.util.Optional;

@Service
@AllArgsConstructor

public class DoctorsServiceImpl implements DoctorsService {

    private final DoctorRepository doctorRepository;

    private final Mapper mapper;

    @Override
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findByRole(Role.DOCTOR).stream().map(doctor -> mapper.doctorToDoctorDto(doctor)).toList();
    }

    @Override
    public DoctorDTO getDoctorById(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        return mapper.doctorToDoctorDto(doctor);
    }

    @Override
    public Doctor createDoctor(Doctor doctor, MultipartFile file) throws IOException {
        Optional<Doctor> doctorFound = doctorRepository.findByEmail(doctor.getEmail());
        if (doctorFound.isPresent()) {
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
    public Doctor createDoctor(Doctor doctor) {
        Optional<Doctor> doctorFound = doctorRepository.findByEmail(doctor.getEmail());
        if (doctorFound.isPresent()) {
            throw new ConflictException("Email already exist, please login or try other one");
        }
        return doctorRepository.save(doctor);
    }


    @Override
    public DoctorDTO updateDoctor(String id, DoctorDTO doctor) {
        Doctor existingDoctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        existingDoctor.setFirstname(doctor.getFirstname());
        existingDoctor.setComment(doctor.getComment());
        existingDoctor.setPhone(doctor.getPhone());
        existingDoctor.setImage(doctor.getImage());
        return mapper.doctorToDoctorDto(doctorRepository.save(existingDoctor));
    }

    @Override
    public void deleteDoctor(String id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        doctorRepository.delete(doctor);
    }

    @Override
    public DoctorDTO addImage(String id, MultipartFile file) throws IOException {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", id));
        String imagesLocation = System.getProperty("user.home") + "/hospital/images";
        File f = new File(imagesLocation);
        if (f.exists()) {
            System.out.println(" directory already exist");
        } else {
            System.out.println("create directories required to store image");
            f.mkdir();
        }
        String imageName = doctor.getEmail() + ".jpg";// while users mail is unique so we will stored their images by their mails
        String imageSrc = imagesLocation + "/" + imageName;
        doctor.setImage(imageSrc);
        Files.write(Paths.get(imageSrc), file.getBytes());// add image in server
        return mapper.doctorToDoctorDto(doctorRepository.save(doctor));
    }
}
