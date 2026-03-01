package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.dto.ReviewRequest;
import com.restapi.store.vehicleregistration.dto.ReviewResponse;
import com.restapi.store.vehicleregistration.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/reviews")
@RequiredArgsConstructor
@Slf4j
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<ReviewResponse> addReview(@Valid @RequestBody ReviewRequest request) {
        log.info("Received request to add review for vehicle {}", request.vehicleId());
        return CompletableFuture.supplyAsync(() -> reviewService.addReview(request));
    }

    @GetMapping("/vehicle/{vehicleId}")
    public CompletableFuture<Page<ReviewResponse>> getReviewsForVehicle(@PathVariable Long vehicleId, Pageable pageable) {
        log.info("Received request to fetch reviews for vehicle {}", vehicleId);
        return CompletableFuture.supplyAsync(() -> reviewService.getReviewsForVehicle(vehicleId, pageable));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> deleteReview(@PathVariable Long id) {
        log.info("Received request to delete review {}", id);
        return CompletableFuture.runAsync(() -> reviewService.deleteReview(id));
    }
}