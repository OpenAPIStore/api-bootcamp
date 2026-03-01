package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.domain.VehicleType;
import com.restapi.store.vehicleregistration.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/inventory")
@RequiredArgsConstructor
@Slf4j
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/total")
    public CompletableFuture<Long> getTotalAvailable(@RequestParam(required = false) VehicleType type) {
        log.info("Request to get total available inventory. Type filter: {}", type);
        return CompletableFuture.supplyAsync(() -> inventoryService.getTotalAvailableVehicles(type));
    }

    @GetMapping("/by-brand")
    public CompletableFuture<Map<String, Long>> getAvailableByBrand(@RequestParam(required = false) VehicleType type) {
        log.info("Request to get available inventory by brand. Type filter: {}", type);
        return CompletableFuture.supplyAsync(() -> inventoryService.getAvailableVehiclesByBrand(type));
    }
}