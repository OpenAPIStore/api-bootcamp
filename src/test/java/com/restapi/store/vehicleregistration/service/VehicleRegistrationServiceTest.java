package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleType;
import com.restapi.store.vehicleregistration.domain.VehicleUser;
import com.restapi.store.vehicleregistration.dto.VehicleRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.VehicleResponse;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import com.restapi.store.vehicleregistration.repository.VehicleUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleRegistrationServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleUserRepository userRepository;

    @InjectMocks
    private VehicleRegistrationService service;

    private VehicleRegistrationRequest request;
    private VehicleUser user;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        request = VehicleRegistrationRequest.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .vehicleType(VehicleType.CAR)
                .brand("Toyota")
                .make("Toyota")
                .model("Camry")
                .cost(BigDecimal.valueOf(25000))
                .manufactureYear(2020)
                .registrationNumber("XYZ1234")
                .build();

        user = VehicleUser.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .build();

        vehicle = Vehicle.builder()
                .id(1L)
                .type(VehicleType.CAR)
                .status(VehicleStatus.AVAILABLE)
                .brand("Toyota")
                .make("Toyota")
                .model("Camry")
                .cost(BigDecimal.valueOf(25000))
                .manufactureYear(2020)
                .registrationNumber("XYZ1234")
                .owner(user)
                .build();
    }

    @Test
    void registerVehicle_NewUser_Success() {
        when(userRepository.findByEmail(request.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(VehicleUser.class))).thenReturn(user);
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(vehicle);

        VehicleResponse response = service.registerVehicle(request);

        assertNotNull(response);
        assertEquals(vehicle.getRegistrationNumber(), response.registrationNumber());
        assertEquals(user.getEmail(), response.owner().email());
        verify(userRepository, times(1)).save(any(VehicleUser.class));
        verify(vehicleRepository, times(1)).save(any(Vehicle.class));
    }

    @Test
    void getVehicleById_Success() {
        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));

        VehicleResponse response = service.getVehicleById(1L);

        assertNotNull(response);
        assertEquals(1L, response.id());
    }
}