package com.example.doctorbackend.services;

import com.example.doctorbackend.entities.Review;

import java.util.List;

public interface ReviewService {
    Review addReview(String doctorId, Review review);
    List<Review> getReviewsByDoctor(String doctorId);
    Double getAverageScore(String doctorId) ;
}
