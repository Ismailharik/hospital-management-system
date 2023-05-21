package com.example.doctorbackend.services;

import com.example.doctorbackend.auth.AuthenticationService;
import com.example.doctorbackend.auth.RegisterRequest;
import com.example.doctorbackend.entities.Category;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.CategoryRepository;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.enums.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AdminService {
    private final AuthenticationService authenticationService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final DoctorRepository doctorRepository;


    public void addDoctor(RegisterRequest request, String categoryId, MultipartFile file) throws IOException {
        // save doctor as user for authentication
        request.setRole(Role.DOCTOR);
        this.authenticationService.register(request,file);
        //get the saved doctor and store him on specific category
        System.out.println(request.toString());
            Optional<Doctor> savedDoctor = doctorRepository.findByEmail(request.getEmail());
            Category category= categoryService.getCategoryById(categoryId);
            if (category==null){
                throw new NotFoundException("Category","id",categoryId);
            }
            category.getDoctors().add(savedDoctor.get());
        categoryRepository.save(category);
    }
}
