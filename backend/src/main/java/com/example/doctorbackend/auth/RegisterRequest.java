package com.example.doctorbackend.auth;

import com.example.doctorbackend.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

  private String firstname;
  private String lastname;
  private String email;
  private String password;
  private String phone;
  private String comment;
  private Role role;// if I want to remove this I should use map this object
}
