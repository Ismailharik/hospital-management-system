package com.example.doctorbackend.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @DBRef
    @Field("doctor_id")
    private Doctor doctor;

    @DBRef
    @Field("patient_id")
    private Patient patient; // should be removed
    private String comment;
    private Double rating;// should range between 0 & 5
}
