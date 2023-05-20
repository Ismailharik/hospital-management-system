package com.example.doctorbackend.dto;


import com.example.doctorbackend.user.Role;
import com.example.doctorbackend.user.User;
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
    private String password;
    private Role role;
    private String comment;
    private String speciality;
    private Double rating;

}

