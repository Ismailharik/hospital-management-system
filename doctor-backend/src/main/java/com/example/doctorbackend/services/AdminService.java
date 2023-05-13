package com.example.doctorbackend.services;

import com.example.doctorbackend.auth.AuthenticationService;
import com.example.doctorbackend.auth.RegisterRequest;
import com.example.doctorbackend.entities.Category;
import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.CategoryRepository;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.user.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AdminService {
    private final AuthenticationService authenticationService;
    private final CategoryService categoryService;
    private final CategoryRepository categoryRepository;
    private final DoctorsService doctorsService;


    public void addDoctor(RegisterRequest request,String categoryId){
        // save doctor as user for authentication
        request.setRole(Role.DOCTOR);
        this.authenticationService.register(request);
        // save doctor on user cluster for authentication ,then in doctors cluster
        Doctor doctor = new Doctor();
        doctor.setEmail(request.getEmail());
        doctor.setSpeciality(request.getSpeciality());
        doctor.setName(request.getFirstname()+" "+request.getLastname());
        Doctor savedDoctor = doctorsService.createDoctor(doctor);

        Category category= categoryService.getCategoryById(categoryId);
        category.getDoctors().add(savedDoctor);
        categoryRepository.save(category);
    }
}
