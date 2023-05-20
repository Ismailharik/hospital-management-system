package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Reservation;

import java.util.List;


public interface ReservationService {
    List<Reservation> getAllReservations();
    Reservation getReservationById(String id);
    Reservation createReservation(Reservation reservation,String patientId,String doctorId);
    Reservation updateReservation(String id, Reservation reservation);
    void deleteReservation(String id);

    List<Reservation> getReservationsOrderedByDate();
    Reservation confirmReservation(String doctorId,String reservationId);

    List<Reservation> getReservationsByDoctorId(String doctorId);

    List<Reservation> getReservationsByPatientId(String patientId);
}
