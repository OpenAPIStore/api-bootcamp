package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleType;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private InventoryService service;

    @BeforeEach
    void setUp() {
        Vehicle v1 = Vehicle.builder().type(VehicleType.CAR).brand("Toyota").status(VehicleStatus.AVAILABLE).build();
        Vehicle v2 = Vehicle.builder().type(VehicleType.CAR).brand("Toyota").status(VehicleStatus.SOLD).build();
        Vehicle v3 = Vehicle.builder().type(VehicleType.BIKE).brand("Honda").status(VehicleStatus.AVAILABLE).build();
        
        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(v1, v2, v3));
    }

    @Test
    void getTotalAvailableVehicles() {
        assertEquals(2, service.getTotalAvailableVehicles(null));
        assertEquals(1, service.getTotalAvailableVehicles(VehicleType.CAR));
        assertEquals(1, service.getTotalAvailableVehicles(VehicleType.BIKE));
    }

    @Test
    void getAvailableVehiclesByBrand() {
        Map<String, Long> byBrand = service.getAvailableVehiclesByBrand(null);
        assertEquals(1, byBrand.get("Toyota"));
        assertEquals(1, byBrand.get("Honda"));
    }
}