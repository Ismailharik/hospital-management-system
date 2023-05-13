package com.example.doctorbackend;

import com.example.doctorbackend.auth.AuthenticationService;
import com.example.doctorbackend.auth.RegisterRequest;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.repositories.PatientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

import static com.example.doctorbackend.user.Role.ADMIN;

@SpringBootApplication
public class DoctorBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DoctorBackendApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PatientRepository pr, AuthenticationService service) {
//		return args -> {
//			pr.saveAll(List.of(
//			new Patient(1L,"ismail1","ismail1@gmail.com","06246721","1234"),
//			new Patient(2L,"ismail2","ismail2@gmail.com","06245561","1234"),
//			new Patient(3L,"ismail3","ismail3@gmail.com","06434671","1234"),
//			new Patient(4L,"ismail4","ismail4@gmail.com","06796981","1234")
//			));
        return args -> {
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin).getAccessToken());
        };
    };
}

