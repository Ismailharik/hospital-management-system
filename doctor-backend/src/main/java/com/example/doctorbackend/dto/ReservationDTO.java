package com.example.doctorbackend.dto;

import com.example.doctorbackend.entities.Patient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDTO {
    private String id;
    private PatientDTO patientDTO;
    private DoctorDTO doctorDTO;
    private Date appointmentDate;
    private Date reservationDate;
    private boolean confirmed;
    private String comment;

}
