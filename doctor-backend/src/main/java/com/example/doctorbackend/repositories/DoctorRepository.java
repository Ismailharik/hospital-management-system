
package com.example.doctorbackend.repositories;

import  com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.user.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;


public interface DoctorRepository extends MongoRepository<Doctor,String> {
    Optional<Doctor> findByEmail(String email);
    List<Doctor> findByRole(Role role);
}
