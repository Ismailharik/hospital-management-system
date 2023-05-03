
package com.example.doctorbackend.repositories;

import com.example.doctorbackend.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface    ReservationRepository extends MongoRepository<Reservation,String> {
}
