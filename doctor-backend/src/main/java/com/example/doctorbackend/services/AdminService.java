package com.example.doctorbackend.services;

import com.example.doctorbackend.auth.AuthenticationService;
import com.example.doctorbackend.auth.RegisterRequest;
import com.example.doctorbackend.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private AuthenticationService authenticationService;


    public void addDoctor(RegisterRequest request){
        request.setRole(Role.DOCTOR);
    this.authenticationService.register(request);
    }
}
