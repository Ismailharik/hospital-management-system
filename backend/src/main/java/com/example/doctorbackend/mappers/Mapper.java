package com.example.doctorbackend.mappers;

import com.example.doctorbackend.dto.DoctorDTO;
import com.example.doctorbackend.dto.PatientDTO;
import com.example.doctorbackend.dto.ReservationDTO;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.entities.Reservation;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class Mapper {

    public Reservation reservationDtoToReservation(ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        BeanUtils.copyProperties(reservationDTO,reservation);
        return reservation;
    }

    public ReservationDTO reservationToReservationDto(Reservation reservation) {
        ReservationDTO reservationDTO = new ReservationDTO();
        BeanUtils.copyProperties(reservation,reservationDTO);
        reservationDTO.setPatientDTO(this.patientToPatientDto(reservation.getPatient()));
        reservationDTO.setDoctorDTO(this.doctorToDoctorDto(reservation.getDoctor()));

        return reservationDTO;
    }

    public DoctorDTO doctorToDoctorDto(Doctor doctor) {
        DoctorDTO doctorDTO = new DoctorDTO();
        BeanUtils.copyProperties(doctor,doctorDTO);
        return doctorDTO;
    }

    public Doctor doctorDtoToDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = new Doctor();
        BeanUtils.copyProperties(doctorDTO,doctor);
        return doctor;
    }

    public PatientDTO patientToPatientDto(Patient patient) {
        PatientDTO patientDto = new PatientDTO();
        BeanUtils.copyProperties(patient,patientDto);
        return patientDto;
    }

    public Patient patientDtoToPatient(PatientDTO patientDto) {
        Patient patient = new Patient();
        BeanUtils.copyProperties(patientDto,patient);
        return patient;
    }
}
