package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleType;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class InventoryService {

    private final VehicleRepository vehicleRepository;

    @Transactional(readOnly = true)
    public long getTotalAvailableVehicles(VehicleType type) {
        log.info("Fetching total available vehicles, filter type: {}", type);
        return vehicleRepository.findAll().stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .filter(v -> type == null || v.getType() == type)
                .count();
    }

    @Transactional(readOnly = true)
    public Map<String, Long> getAvailableVehiclesByBrand(VehicleType type) {
        log.info("Fetching available vehicles by brand, filter type: {}", type);
        return vehicleRepository.findAll().stream()
                .filter(v -> v.getStatus() == VehicleStatus.AVAILABLE)
                .filter(v -> type == null || v.getType() == type)
                .collect(Collectors.groupingBy(
                        v -> v.getBrand() != null ? v.getBrand() : "Unknown",
                        Collectors.counting()
                ));
    }
}