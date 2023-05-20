
package com.example.doctorbackend.repositories;

import  com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface DoctorRepository extends MongoRepository<Doctor,String> {
    Optional<Doctor> findByEmail(String email);
}
