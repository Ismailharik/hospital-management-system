package com.example.doctorbackend.services;

import com.example.doctorbackend.dto.ReservationDTO;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.entities.Reservation;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.mappers.Mapper;
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
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final Mapper mapper;


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
    public ReservationDTO createReservation(ReservationDTO reservationDTO, String patientId, String doctorId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new NotFoundException("Patient", "id", patientId));
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new NotFoundException("Doctor", "id", doctorId));
        Reservation reservation = mapper.reservationDtoToReservation(reservationDTO);
        reservation.setPatient(patient);
        reservation.setDoctor(doctor);
        reservation.setReservationDate(new Date());
        return mapper.reservationToReservationDto(reservationRepository.save(reservation));
    }

    @Override
    public ReservationDTO updateReservation(String id, ReservationDTO reservationDTO) {
        Reservation reservationToUpdate = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        reservationToUpdate.setConfirmed(reservationDTO.isConfirmed());
        reservationToUpdate.setReservationDate(reservationDTO.getReservationDate());
        reservationToUpdate.setAppointmentDate(reservationDTO.getAppointmentDate());
        reservationToUpdate.setComment(reservationDTO.getComment());
        return mapper.reservationToReservationDto(reservationRepository.save(reservationToUpdate));
    }

    @Override
    public void deleteReservation(String id) {
        Reservation reservationToDelete = reservationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Reservation not found with id: " + id));

        reservationRepository.delete(reservationToDelete);
    }

    @Override
    public List<ReservationDTO> getReservationsOrderedByDate() {
        List<Reservation> reservations = reservationRepository.findAll(Sort.by(Sort.Direction.DESC, "reservationDate"));
        List<ReservationDTO> reservationDTOS = reservations.stream().map(
                reservation -> mapper.reservationToReservationDto(reservation)
        ).toList();
        return reservationDTOS;
    }

    @Override
    public ReservationDTO confirmReservation(String doctorId, String reservationId) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor", "id", doctorId));

        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException("Reservation", "id", reservationId));

        reservation.setConfirmed(true);
        return mapper.reservationToReservationDto(reservationRepository.save(reservation));
    }

    @Override
    public List<ReservationDTO> getReservationsByDoctorId(String doctorId) {
        List<ReservationDTO> reservations = reservationRepository.findByDoctorId(doctorId)
                .stream().map(reservation -> mapper.reservationToReservationDto(reservation))
                .toList();
        return reservations;
    }

    @Override
    public List<ReservationDTO> getReservationsByPatientId(String patientId) {
        // normaly I should Use other response
        List<ReservationDTO> reservations = reservationRepository.findByPatientId(patientId)
                .stream().map(reservation -> mapper.reservationToReservationDto(reservation))
                .toList();
        return reservations;
    }
}
