
package com.example.doctorbackend.repositories;

import  com.example.doctorbackend.entities.Doctor;
import org.springframework.data.mongodb.repository.MongoRepository;



public interface DoctorRepository extends MongoRepository<Doctor,String> {
    Doctor findByEmail(String email);
}
