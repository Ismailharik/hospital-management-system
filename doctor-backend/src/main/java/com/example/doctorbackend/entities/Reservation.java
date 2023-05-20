package com.example.doctorbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    @Field("doctor_id")
    private Doctor doctor;
    @DBRef
    @Field("patient_id")
    private Patient patient;
    private String comment;
    private Date appointmentDate;
    private Date reservationDate;
    private boolean confirmed;// reservation will be confirmed by the doctor
}
