package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Admin;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.enums.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface AdminRepository extends MongoRepository<Admin,String> {
    Optional<Patient> findByEmail(String email);
    List<Admin> findByRole(Role role);

}
