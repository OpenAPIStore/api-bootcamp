package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleReview;
import com.restapi.store.vehicleregistration.domain.VehicleUser;
import com.restapi.store.vehicleregistration.dto.ReviewRequest;
import com.restapi.store.vehicleregistration.dto.ReviewResponse;
import com.restapi.store.vehicleregistration.exception.ResourceNotFoundException;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import com.restapi.store.vehicleregistration.repository.VehicleReviewRepository;
import com.restapi.store.vehicleregistration.repository.VehicleUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReviewService {

    private final VehicleReviewRepository reviewRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleUserRepository userRepository;

    @Transactional
    public ReviewResponse addReview(ReviewRequest request) {
        log.info("Adding review for vehicle {} by {}", request.vehicleId(), request.authorEmail());

        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));

        VehicleUser author = userRepository.findByEmail(request.authorEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        VehicleReview review = VehicleReview.builder()
                .vehicle(vehicle)
                .author(author)
                .rating(request.rating())
                .comment(request.comment())
                .build();

        VehicleReview savedReview = reviewRepository.save(review);
        return mapToResponse(savedReview);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponse> getReviewsForVehicle(Long vehicleId, Pageable pageable) {
        log.info("Fetching reviews for vehicle id: {}", vehicleId);
        return reviewRepository.findByVehicleId(vehicleId, pageable).map(this::mapToResponse);
    }

    @Transactional
    public void deleteReview(Long reviewId) {
        log.info("Deleting review id: {}", reviewId);
        if (!reviewRepository.existsById(reviewId)) {
            throw new ResourceNotFoundException("Review not found");
        }
        reviewRepository.deleteById(reviewId);
    }

    private ReviewResponse mapToResponse(VehicleReview review) {
        return ReviewResponse.builder()
                .id(review.getId())
                .vehicleId(review.getVehicle().getId())
                .authorName(review.getAuthor().getFirstName() + " " + review.getAuthor().getLastName())
                .authorProfileImageUrl(review.getAuthor().getProfileImageUrl())
                .rating(review.getRating())
                .comment(review.getComment())
                .createdAt(review.getCreatedAt())
                .build();
    }
}