package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleUser;
import com.restapi.store.vehicleregistration.dto.UserResponse;
import com.restapi.store.vehicleregistration.dto.VehicleRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.VehicleResponse;
import com.restapi.store.vehicleregistration.exception.ResourceNotFoundException;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import com.restapi.store.vehicleregistration.repository.VehicleUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleRegistrationService {

    private final VehicleRepository vehicleRepository;
    private final VehicleUserRepository userRepository;

    @Transactional
    public VehicleResponse registerVehicle(VehicleRegistrationRequest request) {
        log.info("Registering vehicle for email: {}", request.email());

        VehicleUser owner = userRepository.findByEmail(request.email())
                .orElseGet(() -> {
                    VehicleUser newUser = VehicleUser.builder()
                            .firstName(request.firstName())
                            .middleName(request.middleName())
                            .lastName(request.lastName())
                            .email(request.email())
                            .phoneNumber(request.phoneNumber())
                            .paymentType(request.paymentType())
                            .salaried(request.salaried())
                            .communicationAddress(request.communicationAddress())
                            .shippingAddress(request.shippingAddress())
                            .build();
                    return userRepository.save(newUser);
                });

        Vehicle vehicle = Vehicle.builder()
                .type(request.vehicleType())
                .status(VehicleStatus.AVAILABLE)
                .vehicleCondition(request.vehicleCondition())
                .brand(request.brand())
                .make(request.make())
                .model(request.model())
                .manufactureYear(request.manufactureYear())
                .registrationNumber(request.registrationNumber())
                .vin(request.vin())
                .mileage(request.mileage())
                .bodyType(request.bodyType())
                .cost(request.cost())
                .engineCapacity(request.engineCapacity())
                .power(request.power())
                .fuelType(request.fuelType())
                .images(request.images())
                .availablePaymentModes(request.availablePaymentModes())
                .features(request.features())
                .owner(owner)
                .build();

        Vehicle savedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(savedVehicle);
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponse> getAllVehicles(Pageable pageable) {
        log.info("Fetching all vehicles with pagination");
        return vehicleRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<VehicleResponse> searchVehicles(java.math.BigDecimal minPrice, java.math.BigDecimal maxPrice, String brand, Integer minYear, Integer maxYear, com.restapi.store.vehicleregistration.domain.VehicleCondition condition, Integer maxMileage, Pageable pageable) {
        log.info("Searching available vehicles with given criteria");
        return vehicleRepository.searchAvailableVehicles(minPrice, maxPrice, brand, minYear, maxYear, condition, maxMileage, pageable)
                .map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public VehicleResponse getVehicleById(Long id) {
        log.info("Fetching vehicle with id: {}", id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));
        return mapToResponse(vehicle);
    }

    @Transactional
    public VehicleResponse updateVehicle(Long id, VehicleRegistrationRequest request) {
        log.info("Updating vehicle with id: {}", id);
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + id));

        if (request.vehicleType() != null) vehicle.setType(request.vehicleType());
        if (request.vehicleCondition() != null) vehicle.setVehicleCondition(request.vehicleCondition());
        if (request.brand() != null) vehicle.setBrand(request.brand());
        if (request.make() != null) vehicle.setMake(request.make());
        if (request.model() != null) vehicle.setModel(request.model());
        if (request.manufactureYear() != null) vehicle.setManufactureYear(request.manufactureYear());
        if (request.registrationNumber() != null) vehicle.setRegistrationNumber(request.registrationNumber());
        if (request.vin() != null) vehicle.setVin(request.vin());
        if (request.mileage() != null) vehicle.setMileage(request.mileage());
        if (request.bodyType() != null) vehicle.setBodyType(request.bodyType());
        if (request.cost() != null) vehicle.setCost(request.cost());
        if (request.engineCapacity() != null) vehicle.setEngineCapacity(request.engineCapacity());
        if (request.power() != null) vehicle.setPower(request.power());
        if (request.fuelType() != null) vehicle.setFuelType(request.fuelType());
        if (request.images() != null) vehicle.setImages(request.images());
        if (request.availablePaymentModes() != null) vehicle.setAvailablePaymentModes(request.availablePaymentModes());
        if (request.features() != null) vehicle.setFeatures(request.features());

        Vehicle updatedVehicle = vehicleRepository.save(vehicle);
        return mapToResponse(updatedVehicle);
    }

    @Transactional
    public void deleteVehicle(Long id) {
        log.info("Deleting vehicle with id: {}", id);
        if (!vehicleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Vehicle not found with id: " + id);
        }
        vehicleRepository.deleteById(id);
    }

    public VehicleResponse mapToResponse(Vehicle vehicle) {
        UserResponse userResponse = null;
        if (vehicle.getOwner() != null) {
            userResponse = UserResponse.builder()
                    .id(vehicle.getOwner().getId())
                    .firstName(vehicle.getOwner().getFirstName())
                    .middleName(vehicle.getOwner().getMiddleName())
                    .lastName(vehicle.getOwner().getLastName())
                    .email(vehicle.getOwner().getEmail())
                    .phoneNumber(vehicle.getOwner().getPhoneNumber())
                    .paymentType(vehicle.getOwner().getPaymentType())
                    .salaried(vehicle.getOwner().getSalaried())
                    .communicationAddress(vehicle.getOwner().getCommunicationAddress())
                    .shippingAddress(vehicle.getOwner().getShippingAddress())
                    .build();
        }

        return VehicleResponse.builder()
                .id(vehicle.getId())
                .type(vehicle.getType())
                .status(vehicle.getStatus())
                .vehicleCondition(vehicle.getVehicleCondition())
                .brand(vehicle.getBrand())
                .make(vehicle.getMake())
                .model(vehicle.getModel())
                .manufactureYear(vehicle.getManufactureYear())
                .registrationNumber(vehicle.getRegistrationNumber())
                .vin(vehicle.getVin())
                .mileage(vehicle.getMileage())
                .bodyType(vehicle.getBodyType())
                .cost(vehicle.getCost())
                .engineCapacity(vehicle.getEngineCapacity())
                .power(vehicle.getPower())
                .fuelType(vehicle.getFuelType())
                .images(vehicle.getImages())
                .availablePaymentModes(vehicle.getAvailablePaymentModes())
                .features(vehicle.getFeatures())
                .owner(userResponse)
                .createdAt(vehicle.getCreatedAt())
                .build();
    }
}