package com.example.doctorbackend.services;


import com.example.doctorbackend.entities.Doctor;
import com.example.doctorbackend.entities.Review;
import com.example.doctorbackend.error.NotFoundException;
import com.example.doctorbackend.repositories.DoctorRepository;
import com.example.doctorbackend.repositories.ReviewRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewServiceImpl implements ReviewService {



    private ReviewRepository reviewRepository;


    private DoctorRepository doctorRepository;

    public Review addReview(String doctorId, Review review) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() ->  new NotFoundException("Doctor","id",doctorId));
        review.setDoctor(doctor);
        return reviewRepository.save(review);
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
