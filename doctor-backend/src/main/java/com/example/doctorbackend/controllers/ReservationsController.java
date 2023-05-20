package com.example.doctorbackend.controllers;

import com.example.doctorbackend.dto.ReservationDTO;
import com.example.doctorbackend.entities.Reservation;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.mappers.Mapper;
import com.example.doctorbackend.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservations")
@AllArgsConstructor
public class ReservationsController {

    private final ReservationService reservationService;
    private Mapper mapper;



    @GetMapping
    public List<ReservationDTO> getAllReservations() {

        List<ReservationDTO> reservationsDto = reservationService.getAllReservations().stream().map(reservation -> mapper.reservationToReservationDto(reservation)).collect(Collectors.toList());
        return reservationsDto;
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
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservation,
                                                         @RequestParam String patientId,
                                                         @RequestParam String doctorId)
     {
        ReservationDTO createdReservation = reservationService.createReservation(reservation,patientId,doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservationDTO> updateReservation(@PathVariable String id, @RequestBody ReservationDTO reservation) {
        ReservationDTO updatedReservation = reservationService.updateReservation(id, reservation);
        return ResponseEntity.ok(updatedReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable String id) throws NotFoundException {
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/orderedByDate")
    public ResponseEntity<List<ReservationDTO>> getReservationsOrderedByDate() {
        List<ReservationDTO> reservations = reservationService.getReservationsOrderedByDate();
        return ResponseEntity.ok(reservations);
    }
    @GetMapping("/filteredByDoctor/{doctorId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByDoctorId(@PathVariable String doctorId){
        // get reservations specific to a doctor
        List<ReservationDTO> reservations = reservationService.getReservationsByDoctorId(doctorId);
        return ResponseEntity.ok(reservations);
    }
    @PutMapping("/confirm/{doctorId}/{reservationId}")
    public ResponseEntity<ReservationDTO> confirmReservation(@PathVariable String doctorId, @PathVariable String reservationId) {
        ReservationDTO confirmedReservation = reservationService.confirmReservation(doctorId, reservationId);
        return ResponseEntity.ok(confirmedReservation);
    }
    @GetMapping("/filteredByPatient/{patientId}")
    public ResponseEntity<List<ReservationDTO>> getReservationsByPatientId(@PathVariable String patientId){
        // get reservations specific to a doctor
        List<ReservationDTO> reservations = reservationService.getReservationsByPatientId(patientId);
        return ResponseEntity.ok(reservations);
    }
}
