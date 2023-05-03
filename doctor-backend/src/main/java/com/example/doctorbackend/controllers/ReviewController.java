package com.example.doctorbackend.controllers;

import com.example.doctorbackend.entities.Review;
import com.example.doctorbackend.services.ReviewService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@AllArgsConstructor
public class ReviewController {


    private ReviewService reviewService;

    @PostMapping("/{doctorId}")
    public ResponseEntity<Review> addReview(@PathVariable String doctorId, @RequestBody Review review) {
        Review createdReview = reviewService.addReview(doctorId, review);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdReview);
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
