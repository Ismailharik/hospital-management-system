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

    @Id
    private String id;
    @NotNull(message = "Doctor name required")
    private String name;
    @Email(message = "Please enter a valid email")
    @NotNull(message = "Doctor email required")
    private String email;
    private String speciality;
    // initializing values
    private Double rating=0D;//rating Percent
    private Long numberOfRatings=0L; //sum of all rates
    private Double totalRating=0D; //nbr of patients rated
    private String phone;
    private String image;

}
