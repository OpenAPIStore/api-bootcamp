package com.restapi.store.vehicleregistration.repository;

import com.restapi.store.vehicleregistration.domain.VehicleMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleMediaRepository extends JpaRepository<VehicleMedia, Long> {
}