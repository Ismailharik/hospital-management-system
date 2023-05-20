package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Reservation;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;



    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable String id) {
        try {
            Reservation reservation = reservationService.getReservationById(id);
            return ResponseEntity.ok(reservation);
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation,
                                                         @RequestParam String patientId,
                                                         @RequestParam String doctorId)
     {
        Reservation createdReservation = reservationService.createReservation(reservation,patientId,doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable String id, @RequestBody Reservation reservation) {
        Reservation updatedReservation = reservationService.updateReservation(id, reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) throws NotFoundException {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/orderedByDate")
    public ResponseEntity<List<Reservation>> getReservationsOrderedByDate() {
        List<Reservation> reservations = reservationService.getReservationsOrderedByDate();
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/filteredByDoctor/{doctorId}")
    public ResponseEntity<List<Reservation>> getReservationsByDoctorId(@PathVariable String doctorId){
        // get reservations specific to a doctor
        List<Reservation> reservations = reservationService.getReservationsByDoctorId(doctorId);
        return ResponseEntity.ok(reservations);
    }
    @PutMapping("/confirm/{doctorId}/{reservationId}")
    public ResponseEntity<Reservation> confirmReservation(@PathVariable String doctorId, @PathVariable String reservationId) {
        Reservation confirmedReservation = reservationService.confirmReservation(doctorId, reservationId);
        return ResponseEntity.ok(confirmedReservation);
    }
    @GetMapping("/filteredByPatient/{patientId}")
    public ResponseEntity<List<Reservation>> getReservationsByPatientId(@PathVariable String patientId){
        // get reservations specific to a doctor
        List<Reservation> reservations = reservationService.getReservationsByPatientId(patientId);
        return ResponseEntity.ok(reservations);
    }
}
