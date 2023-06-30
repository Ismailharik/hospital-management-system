package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Review;
import com.example.doctorbackend.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
@AllArgsConstructor
public class ReviewController {


    private ReviewService reviewService;

    @PostMapping("/doctors/{doctorId}/reviews")
    public ResponseEntity<Review> addReviewForDoctor(@PathVariable String doctorId,
                                                     @RequestParam String patientId,
                                                     @RequestBody Review review) {
        Review addedReview = reviewService.addReview(doctorId, patientId, review);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedReview);
    }


    @GetMapping("/{doctorId}")
    public ResponseEntity<List<Review>> getReviewsByDoctor(@PathVariable String doctorId) {
        List<Review> reviews = reviewService.getReviewsByDoctor(doctorId);
        return ResponseEntity.ok(reviews);
    }

    @GetMapping("/{doctorId}/averageScore")
    public ResponseEntity<Double> getAverageScore(@PathVariable String doctorId) {
        Double averageScore = reviewService.getAverageScore(doctorId);
        return ResponseEntity.ok(averageScore);
    }
}
