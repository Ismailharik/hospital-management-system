package com.example.doctorbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @ManyToOne
    private Doctor doctor;
    @ManyToOne
    private Patient patient;
    private Date appointmentDate;
    private Date reservationDate;
    private boolean confirmed;// reservation will be confirmed by the doctor
}
