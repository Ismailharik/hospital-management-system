package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Patient;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface PatientRepository extends MongoRepository<Patient,String> {
    Patient findByEmail(String email);
}
