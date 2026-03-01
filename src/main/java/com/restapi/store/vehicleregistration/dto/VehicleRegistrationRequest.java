package com.restapi.store.vehicleregistration.dto;

import com.restapi.store.vehicleregistration.domain.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record VehicleRegistrationRequest(
        // User Info (Owner)
        @NotBlank(message = "First name is required")
        String firstName,
        String middleName,
        @NotBlank(message = "Last name is required")
        String lastName,
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        String phoneNumber,
        PaymentType paymentType,
        Boolean salaried,
        Address communicationAddress,
        Address shippingAddress,
        
        // Vehicle Info
        @NotNull(message = "Vehicle type is required")
        VehicleType vehicleType,
        VehicleCondition vehicleCondition,
        @NotBlank(message = "Brand is required")
        String brand,
        String make,
        String model,
        Integer manufactureYear,
        @NotBlank(message = "Registration number is required")
        String registrationNumber,
        String vin,
        @PositiveOrZero(message = "Mileage must be zero or positive")
        Integer mileage,
        String bodyType,
        @PositiveOrZero(message = "Cost must be zero or positive")
        BigDecimal cost,
        @PositiveOrZero(message = "Engine capacity must be zero or positive")
        Integer engineCapacity,
        @PositiveOrZero(message = "Power must be zero or positive")
        Integer power,
        FuelType fuelType,
        List<String> images,
        List<PaymentType> availablePaymentModes,
        VehicleFeatures features
) {}