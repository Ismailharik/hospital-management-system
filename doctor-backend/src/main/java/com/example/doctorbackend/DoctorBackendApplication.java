package com.example.doctorbackend;

import com.example.doctorbackend.auth.AuthenticationService;
import com.example.doctorbackend.auth.RegisterRequest;
import com.example.doctorbackend.entities.Category;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.repositories.CategoryRepository;


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
    CommandLineRunner runner(
            CategoryRepository categoryRepository,

            AuthenticationService service) {
        return args -> {

//            Category category1 = new Category();
//            category1.setName("Cardiology");
//            Category category2 = new Category();
//            category2.setName("Dermatology");
//            Category category3 = new Category();
//            category3.setName("Endocrinology");
//            Category category4 = new Category();
//            category4.setName("Pediatrics");
//            categoryRepository.saveAll(List.of(category1, category2, category3, category4));
//
//
            var admin = RegisterRequest.builder()
                    .firstname("Admin")
                    .lastname("Admin")
                    .email("admin@mail.com")
                    .password("password")
                    .role(ADMIN)
                    .build();
            System.out.println("Admin token: " + service.register(admin,null).getAccessToken());
        };
    }

    ;
}

