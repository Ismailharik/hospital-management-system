package com.example.doctorbackend.auth;

import com.example.doctorbackend.config.JwtService;
import com.example.doctorbackend.entities.Admin;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.repositories.AdminRepository;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.services.DoctorsService;
import com.example.doctorbackend.services.PatientsService;
import com.example.doctorbackend.token.Token;
import com.example.doctorbackend.token.TokenRepository;
import com.example.doctorbackend.token.TokenType;
import com.example.doctorbackend.user.Role;
import com.example.doctorbackend.user.User;
import com.example.doctorbackend.user.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PatientsService patientsService;
    private final DoctorsService doctorsService;
    private final DoctorRepository doctorRepository;
    private final AdminRepository adminRepository;


    public AuthenticationResponse register(RegisterRequest request) {

//        var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole())
//                .build();
        if (request.getRole().equals(Role.USER)) {
            Patient patient = new Patient();
            patient.setFirstname(request.getFirstname() + " " + request.getLastname());
            patient.setEmail(request.getEmail());
            patient.setPassword(passwordEncoder.encode(request.getPassword()));
            patient.setPhone(request.getPhone());
            patient.setRole(request.getRole());
            Patient savedPatient=patientsService.addPatient(patient);
            var jwtToken = jwtService.generateToken(patient);
            var refreshToken = jwtService.generateRefreshToken(patient);
            saveUserToken(savedPatient, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else if (request.getRole().equals(Role.DOCTOR)) {
            Doctor doctor = new Doctor();
            doctor.setFirstname(request.getFirstname() + " " + request.getLastname());
            doctor.setEmail(request.getEmail());
            doctor.setPassword(passwordEncoder.encode(request.getPassword()));
            doctor.setPhone(request.getPhone());
            doctor.setRole(request.getRole());
            doctor.setPhone(request.getPhone());
            Doctor savedDoctor=doctorsService.createDoctor(doctor);
            var jwtToken = jwtService.generateToken(doctor);
            var refreshToken = jwtService.generateRefreshToken(doctor);
            saveUserToken(savedDoctor, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else if (request.getRole().equals(Role.ADMIN)) {
            //Admin will be stored only one time no need for it
            Admin admin = new Admin();
            admin.setFirstname(request.getFirstname() + " " + request.getLastname());
            admin.setEmail(request.getEmail());
            admin.setPassword(passwordEncoder.encode(request.getPassword()));
            admin.setPhone(request.getPhone());
            admin.setRole(request.getRole());
            admin.setPhone(request.getPhone());
            var savedAdmin = adminRepository.save(admin);
            var jwtToken = jwtService.generateToken(admin);
            var refreshToken = jwtService.generateRefreshToken(admin);
            saveUserToken(savedAdmin, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        }





        // save doc
        return null;

    }

    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        User user=null;
        if (request.getRole().equals(Role.ADMIN)){
            user = adminRepository.findByEmail(request.getEmail())
                    .orElseThrow();
        }else if(request.getRole().equals(Role.DOCTOR)){
            user = doctorRepository.findByEmail(request.getEmail())
                    .orElseThrow();
        }else if(request.getRole().equals(Role.USER)){
             user = patientsService.getPatientByEmail(request.getEmail());

        }


        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, jwtToken);
        return AuthenticationResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    private void saveUserToken(User user, String jwtToken) {
        var token = Token.builder()
                .user(user)
                .token(jwtToken)
                .tokenType(TokenType.BEARER)
                .expired(false)
                .revoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllUserTokens(User user) {
        var validUserTokens = tokenRepository.findAllValidTokenByUser(user.getId());
        if (validUserTokens.isEmpty())
            return;
        validUserTokens.forEach(token -> {
            token.setExpired(true);
            token.setRevoked(true);
        });
        tokenRepository.saveAll(validUserTokens);
    }

//    public void refreshToken(
//            HttpServletRequest request,
//            HttpServletResponse response
//    ) throws IOException {
//        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
//        final String refreshToken;
//        final String userEmail;
//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return;
//        }
//        refreshToken = authHeader.substring(7);
//        userEmail = jwtService.extractUsername(refreshToken);
//        if (userEmail != null) {
//            var user = this.repository.findByEmail(userEmail)
//                    .orElseThrow();
//            if (jwtService.isTokenValid(refreshToken, user)) {
//                var accessToken = jwtService.generateToken(user);
//                revokeAllUserTokens(user);
//                saveUserToken(user, accessToken);
//                var authResponse = AuthenticationResponse.builder()
//                        .accessToken(accessToken)
//                        .refreshToken(refreshToken)
//                        .build();
//                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
//            }
//        }
//    }
}
