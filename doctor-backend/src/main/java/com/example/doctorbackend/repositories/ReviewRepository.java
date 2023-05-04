package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.entities.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {
    Review findByDoctorAndPatient(Doctor doctor, Patient patient);
    List<Review> findByDoctor(Doctor doctor);
}
