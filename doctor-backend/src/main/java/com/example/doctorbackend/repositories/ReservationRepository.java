
package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface    ReservationRepository extends MongoRepository<Reservation,String> {
    List<Reservation> findByDoctorId(String doctorId);

    List<Reservation> findByPatientId(String patientId);
}
