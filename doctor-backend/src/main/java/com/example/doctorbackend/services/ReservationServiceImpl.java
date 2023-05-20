package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.entities.Reservation;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.repositories.PatientRepository;
import com.example.doctorbackend.repositories.ReservationRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor

public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;
    private  final PatientRepository patientRepository;
    private  final DoctorRepository doctorRepository;



    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    public Reservation getReservationById(String id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));
    }

    @Override
    public Reservation createReservation(Reservation reservation,String patientId,String doctorId) {
        Patient patient=patientRepository.findById(patientId).orElseThrow(()->new NotFoundException("Patient","id",patientId));
        Doctor doctor=doctorRepository.findById(doctorId).orElseThrow(()->new NotFoundException("Doctor","id",doctorId));
        reservation.setPatient(patient);
        reservation.setDoctor(doctor);
        reservation.setReservationDate(new Date());
        return reservationRepository.save(reservation);
    }

    @Override
    public Reservation updateReservation(String id, Reservation reservation) {
        Reservation reservationToUpdate = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        reservationToUpdate.setConfirmed(reservation.isConfirmed());
        reservationToUpdate.setReservationDate(reservation.getReservationDate());
        reservationToUpdate.setAppointmentDate(reservation.getAppointmentDate());
        reservationToUpdate.setDoctor(reservation.getDoctor());
        reservationToUpdate.setPatient(reservation.getPatient());

        return reservationRepository.save(reservationToUpdate);
    }

    @Override
    public void deleteReservation(String id) {
        Reservation reservationToDelete = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        reservationRepository.delete(reservationToDelete);
    }

    @Override
    public List<Reservation> getReservationsOrderedByDate() {
        List<Reservation> reservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "reservationDate"));
        return reservations;
    }

    @Override
    public Reservation confirmReservation(String doctorId, String reservationId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", doctorId));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation", "id", reservationId));

        reservation.setConfirmed(true);
        return reservationRepository.save(reservation);
    }

    @Override
    public List<Reservation> getReservationsByDoctorId(String doctorId) {
        List<Reservation> reservations = reservationRepository.findByDoctorId(doctorId);
        return reservations;
    }

    @Override
    public List<Reservation> getReservationsByPatientId(String patientId) {
        List<Reservation> reservations = reservationRepository.findByPatientId(patientId);
        return reservations;
    }
}
