package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.ReservationDTO;
import com.example.doctorbackend.entities.Reservation;

import java.util.List;


public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservationById(String id);
    ReservationDTO createReservation(ReservationDTO reservation, String patientId, String doctorId);
    ReservationDTO updateReservation(String id, ReservationDTO reservation);
    void deleteReservation(String id);

    List<ReservationDTO> getReservationsOrderedByDate();
    ReservationDTO confirmReservation(String doctorId, String reservationId);

    List<ReservationDTO> getReservationsByDoctorId(String doctorId);

    List<ReservationDTO> getReservationsByPatientId(String patientId);
}
