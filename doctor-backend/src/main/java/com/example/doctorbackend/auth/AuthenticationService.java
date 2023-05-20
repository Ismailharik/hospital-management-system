package com.example.doctorbackend.auth;

import com.example.doctorbackend.config.JwtService;
import com.example.doctorbackend.entities.Admin;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.repositories.AdminRepository;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.repositories.PatientRepository;
//import com.example.doctorbackend.repositories.UserRepository;
import com.example.doctorbackend.services.DoctorsService;
import com.example.doctorbackend.services.PatientsService;
import com.example.doctorbackend.token.Token;
import com.example.doctorbackend.token.TokenRepository;
import com.example.doctorbackend.token.TokenType;
import com.example.doctorbackend.user.Role;
import com.example.doctorbackend.user.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
//    private final UserRepository userRepository;


    public AuthenticationResponse register(RegisterRequest request, MultipartFile file) throws IOException {

//        var user = User.builder()
//                .firstname(request.getFirstname())
//                .lastname(request.getLastname())
//                .email(request.getEmail())
//                .password(passwordEncoder.encode(request.getPassword()))
//                .role(request.getRole())
//                .build();
        if (request.getRole().equals(Role.USER)) {
            Patient patient = new Patient();
            patient.setFirstname(request.getFirstname());
            patient.setLastname(request.getLastname());
            patient.setEmail(request.getEmail());
            patient.setPassword(passwordEncoder.encode(request.getPassword()));
            patient.setPhone(request.getPhone());
            patient.setRole(request.getRole());
            Patient savedPatient=patientsService.addPatient(patient,file);
            var jwtToken = jwtService.generateToken(patient);
            var refreshToken = jwtService.generateRefreshToken(patient);
            saveUserToken(savedPatient, jwtToken);
            return AuthenticationResponse.builder()
                    .accessToken(jwtToken)
                    .refreshToken(refreshToken)
                    .build();
        } else if (request.getRole().equals(Role.DOCTOR)) {
            Doctor doctor = new Doctor();
            doctor.setFirstname(request.getFirstname());
            doctor.setLastname(request.getLastname());
            doctor.setEmail(request.getEmail());
            doctor.setPassword(passwordEncoder.encode(request.getPassword()));
            doctor.setPhone(request.getPhone());
            doctor.setRole(request.getRole());
            doctor.setPhone(request.getPhone());
            Doctor savedDoctor=doctorsService.createDoctor(doctor,file);
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
            admin.setFirstname(request.getFirstname());
            admin.setLastname(request.getLastname());
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
        System.out.println(user.toString());

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
//        tokenRepository.saveAll(validUserTokens);
        tokenRepository.deleteAll(validUserTokens);
        /*
        * In the course you have followed, they set tokens to false as a way to handle reservations. However,
        *  in my case, I have decided to delete the tokens instead.
        * This is because keeping the tokens in the database would only occupy space without serving any purpose.
        * */
    }

    public void refreshToken(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return;
        }
        refreshToken = authHeader.substring(7);
        userEmail = jwtService.extractUsername(refreshToken);
        if (userEmail != null) {
            // you can use every repo since all of them below to people cluster
            var user = this.adminRepository.findByEmail(userEmail)
                    .orElseThrow();
            if (jwtService.isTokenValid(refreshToken, user)) {
                var accessToken = jwtService.generateToken(user);
                revokeAllUserTokens(user);
                saveUserToken(user, accessToken);
                var authResponse = AuthenticationResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                        .build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }
}
