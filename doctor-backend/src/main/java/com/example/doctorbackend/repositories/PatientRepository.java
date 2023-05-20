package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Admin;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface PatientRepository extends MongoRepository<Patient,String> {
    Optional<Patient> findByEmail(String email);
    List<Patient> findByRole(Role role);

}
