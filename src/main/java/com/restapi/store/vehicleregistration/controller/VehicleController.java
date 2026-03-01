package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.dto.VehicleRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.VehicleResponse;
import com.restapi.store.vehicleregistration.service.VehicleRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Slf4j
public class VehicleController {

    private final VehicleRegistrationService vehicleRegistrationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<VehicleResponse> registerVehicle(@Valid @RequestBody VehicleRegistrationRequest request) {
        log.info("Received request to register vehicle");
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.registerVehicle(request));
    }

    @GetMapping
    public CompletableFuture<Page<VehicleResponse>> getAllVehicles(Pageable pageable) {
        log.info("Received request to fetch all vehicles");
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.getAllVehicles(pageable));
    }

    @GetMapping("/search")
    public CompletableFuture<Page<VehicleResponse>> searchVehicles(
            @RequestParam(required = false) java.math.BigDecimal minPrice,
            @RequestParam(required = false) java.math.BigDecimal maxPrice,
            @RequestParam(required = false) String brand,
            @RequestParam(required = false) Integer minYear,
            @RequestParam(required = false) Integer maxYear,
            @RequestParam(required = false) com.restapi.store.vehicleregistration.domain.VehicleCondition condition,
            @RequestParam(required = false) Integer maxMileage,
            Pageable pageable) {
        log.info("Received request to search vehicles");
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.searchVehicles(minPrice, maxPrice, brand, minYear, maxYear, condition, maxMileage, pageable));
    }

    @GetMapping("/{id}")
    public CompletableFuture<VehicleResponse> getVehicleById(@PathVariable Long id) {
        log.info("Received request to fetch vehicle by id: {}", id);
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.getVehicleById(id));
    }

    @PutMapping("/{id}")
    public CompletableFuture<VehicleResponse> putVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRegistrationRequest request) {
        log.info("Received request to update (PUT) vehicle by id: {}", id);
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.updateVehicle(id, request));
    }

    @PatchMapping("/{id}")
    public CompletableFuture<VehicleResponse> patchVehicle(@PathVariable Long id, @Valid @RequestBody VehicleRegistrationRequest request) {
        log.info("Received request to update (PATCH) vehicle by id: {}", id);
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.updateVehicle(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> deleteVehicle(@PathVariable Long id) {
        log.info("Received request to delete vehicle by id: {}", id);
        return CompletableFuture.runAsync(() -> vehicleRegistrationService.deleteVehicle(id));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.OPTIONS)
    public CompletableFuture<org.springframework.http.ResponseEntity<?>> optionsForVehicle() {
        log.info("Received OPTIONS request for vehicle endpoints");
        return CompletableFuture.completedFuture(
                org.springframework.http.ResponseEntity.ok()
                        .allow(org.springframework.http.HttpMethod.GET, 
                               org.springframework.http.HttpMethod.POST, 
                               org.springframework.http.HttpMethod.PUT, 
                               org.springframework.http.HttpMethod.PATCH, 
                               org.springframework.http.HttpMethod.DELETE, 
                               org.springframework.http.HttpMethod.OPTIONS, 
                               org.springframework.http.HttpMethod.HEAD)
                        .build());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.HEAD)
    public CompletableFuture<org.springframework.http.ResponseEntity<?>> headForVehicle(@PathVariable Long id) {
        log.info("Received HEAD request for vehicle id: {}", id);
        return getVehicleById(id).thenApply(vehicle -> org.springframework.http.ResponseEntity.ok().build());
    }
}