package com.example.doctorbackend.dto;


import com.example.doctorbackend.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDTO {
    private String id;
    private String firstname;
    private String lastname;
    private String phone;
    private String image;
    private String email;
    private Role role;
    private String comment;
    private String speciality;
    private Double rating;
    // if you don't want to use jsonIgnore you should you use DoctorResponse and DoctorRequest
    @JsonIgnore
    private String password;

}

