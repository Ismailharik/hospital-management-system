package com.example.doctorbackend.dto;


import com.example.doctorbackend.user.Role;
import com.example.doctorbackend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PatientDto {
    private String id;
    private String firstname;
    private String lastname;
    private String phone;
    private String image;
    private String email;
    private Role role;

}

