package com.restapi.store.vehicleregistration.repository;

import com.restapi.store.vehicleregistration.domain.VehicleUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VehicleUserRepository extends JpaRepository<VehicleUser, Long> {
    Optional<VehicleUser> findByEmail(String email);
}