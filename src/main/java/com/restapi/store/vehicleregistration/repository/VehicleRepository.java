package com.restapi.store.vehicleregistration.repository;

import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
    Optional<Vehicle> findByRegistrationNumber(String registrationNumber);

    @Query("SELECT v FROM Vehicle v WHERE " +
           "(:minPrice IS NULL OR v.cost >= :minPrice) AND " +
           "(:maxPrice IS NULL OR v.cost <= :maxPrice) AND " +
           "(:brand IS NULL OR v.brand = :brand) AND " +
           "(:minYear IS NULL OR v.manufactureYear >= :minYear) AND " +
           "(:maxYear IS NULL OR v.manufactureYear <= :maxYear) AND " +
           "(:condition IS NULL OR v.vehicleCondition = :condition) AND " +
           "(:maxMileage IS NULL OR v.mileage <= :maxMileage) AND " +
           "v.status = 'AVAILABLE'")
    Page<Vehicle> searchAvailableVehicles(
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("brand") String brand,
            @Param("minYear") Integer minYear,
            @Param("maxYear") Integer maxYear,
            @Param("condition") VehicleCondition condition,
            @Param("maxMileage") Integer maxMileage,
            Pageable pageable
    );
}