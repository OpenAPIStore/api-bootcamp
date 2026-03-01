package com.restapi.store.vehicleregistration.repository;

import com.restapi.store.vehicleregistration.domain.VehicleTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleTransactionRepository extends JpaRepository<VehicleTransaction, Long> {
    Page<VehicleTransaction> findByUserEmail(String email, Pageable pageable);
}