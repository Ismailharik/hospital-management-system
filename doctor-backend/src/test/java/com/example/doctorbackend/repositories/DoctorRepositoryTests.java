package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Doctor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class DoctorRepositoryTests {

    @Autowired
    private DoctorRepository doctorRepository;

    @Test
    public void createDoctorTest() {
        Doctor doctor = new Doctor();
        doctor.setName("test name");
        doctor.setEmail("testname@example.com");
        doctor.setSpeciality("Cardiology");
        doctor.setRating(4.5);
        doctor.setPhone("123456789");
        doctor.setImage("https://example.com/john.jpg");

        Doctor savedDoctor = doctorRepository.save(doctor);

        assertNotNull(savedDoctor.getId());
        assertEquals(doctor.getName(), savedDoctor.getName());
        assertEquals(doctor.getEmail(), savedDoctor.getEmail());
        assertEquals(doctor.getSpeciality(), savedDoctor.getSpeciality());
        assertEquals(doctor.getRating(), savedDoctor.getRating(), 0.001);
        assertEquals(doctor.getPhone(), savedDoctor.getPhone());
        assertEquals(doctor.getImage(), savedDoctor.getImage());
    }

    @Test
    void testFindDoctorByEmail() {
        Doctor doctor = new Doctor();
        doctor.setName("John Doe");
        doctor.setEmail("john.doe@example.com");

        doctorRepository.save(doctor);

        Doctor foundDoctor = doctorRepository.findByEmail("john.doe@example.com");
        assertNotNull(foundDoctor.getId());
        assertEquals(doctor.getName(), foundDoctor.getName());
        assertEquals(doctor.getEmail(), foundDoctor.getEmail());
    }

    @Test
    void testFindDoctorByEmailNotFound() {
        Doctor foundDoctor = doctorRepository.findByEmail("nonexistent@example.com");
        assertNull(foundDoctor);

        // delete doctor that was added for test purpose
        Doctor doctor = doctorRepository.findByEmail("john.doe@example.com");
        if (doctor != null) {
            doctorRepository.delete(doctor);
        }
    }

    @AfterEach
    void resetDatabase(){
        // delete doctor that was added for test purpose
        Doctor doctor = doctorRepository.findByEmail("test.name@example.com");
        if (doctor != null) {
            doctorRepository.delete(doctor);
        }
    }
}
