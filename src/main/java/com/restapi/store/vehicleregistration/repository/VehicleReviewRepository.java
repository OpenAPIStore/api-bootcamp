package com.restapi.store.vehicleregistration.repository;

import com.restapi.store.vehicleregistration.domain.VehicleReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleReviewRepository extends JpaRepository<VehicleReview, Long> {
    Page<VehicleReview> findByVehicleId(Long vehicleId, Pageable pageable);
}