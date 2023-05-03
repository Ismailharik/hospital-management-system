package com.example.doctorbackend.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;
    @NotNull(message = "Doctor name required")
    private String name;
    @Email(message = "Please enter a valid email")
    @NotNull(message = "Doctor email required")
    private String email;
    private String speciality;
    private String phone;
    private Double review;
    private Double totalScore;
    private Double satisfaction;
    private String image;
}
