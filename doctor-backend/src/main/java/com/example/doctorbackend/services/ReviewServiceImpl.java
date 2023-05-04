package com.example.doctorbackend.services;


import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Patient;
import com.example.doctorbackend.entities.Review;
import com.example.doctorbackend.error.ConflictException;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.repositories.PatientRepository;
import com.example.doctorbackend.repositories.ReviewRepository;
import org.springframework.dao.DuplicateKeyException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {



    private ReviewRepository reviewRepository;
    private PatientRepository patientRepository;


    private DoctorRepository doctorRepository;

    public Review addReview(String doctorId, String patientId, Review review) {
        Doctor doctor = doctorRepository.findById(doctorId)
                .orElseThrow(() -> new NotFoundException("Doctor","id",doctorId));
        Patient patient = patientRepository.findById(patientId)
                .orElseThrow(() -> new NotFoundException("Patient","id",patientId));

        // Check if the patient has already reviewed this doctor
        Review existingReviews = reviewRepository.findByDoctorAndPatient(doctor, patient);
        if (existingReviews!=null) {
            throw new ConflictException("Patient has already reviewed this doctor");
        }
        review.setDoctor(doctor);
        review.setPatient(patient);
        Review savedReview=reviewRepository.save(review);

        doctor.setTotalRating(doctor.getTotalRating()+review.getRating()); // update sum so all rates
        doctor.setNumberOfRatings(doctor.getNumberOfRatings()+1);// increase nbr of rating by 1

        double averageRating  = (doctor.getTotalRating()) / (doctor.getNumberOfRatings());
        double ratingPerCent=(averageRating / 5.0) * 100.0;
        doctor.setRating(ratingPerCent);
        doctorRepository.save(doctor);

        return savedReview;
    }


    public List<Review> getReviewsByDoctor(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new NotFoundException("Doctor","id",doctorId));
        return reviewRepository.findByDoctor(doctor);
    }

    public Double getAverageScore(String doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->  new NotFoundException("Doctor","id",doctorId));
        List<Review> reviews = reviewRepository.findByDoctor(doctor);
        if (reviews.isEmpty()) {
            return 0D;
        }
        double sum = 0;
        for (Review review : reviews) {
            sum += review.getRating();
        }
        return sum / reviews.size();
    }

}
