package com.example.doctorbackend.entities;

import com.example.doctorbackend.user.User;

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

//    @Id
//    private String id;
//    @NotNull(message = "Doctor name required")
//    private String name;
//    @Email(message = "Please enter a valid email")
//    @NotNull(message = "Doctor email required")
//    @Column(unique = true)
//    private String email;
    private String comment;
    private String speciality;
    // initializing values
    private Double rating=0D;//rating Percent
    private Long numberOfRatings=0L; //sum of all rates
    private Double totalRating=0D; //nbr of patients rated


}
