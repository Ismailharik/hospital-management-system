package com.example.doctorbackend.entities;

import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor

@Document(collection = "people")
@TypeAlias("doctor")
public class Doctor extends User {
    private String comment;
    private String speciality;
    // initializing values
    private Double rating=0D;//rating Percent
    private Long numberOfRatings=0L; //sum of all rates
    private Double totalRating=0D; //nbr of patients rated
}
