package com.restapi.store.vehicleregistration.dto;

import com.restapi.store.vehicleregistration.domain.FuelType;
import com.restapi.store.vehicleregistration.domain.PaymentType;
import com.restapi.store.vehicleregistration.domain.VehicleCondition;
import com.restapi.store.vehicleregistration.domain.VehicleFeatures;
import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Builder
public record VehicleResponse(
        Long id,
        VehicleType type,
        VehicleStatus status,
        VehicleCondition vehicleCondition,
        String brand,
        String make,
        String model,
        Integer manufactureYear,
        String registrationNumber,
        String vin,
        Integer mileage,
        String bodyType,
        BigDecimal cost,
        Integer engineCapacity,
        Integer power,
        FuelType fuelType,
        List<String> images,
        List<PaymentType> availablePaymentModes,
        VehicleFeatures features,
        UserResponse owner,
        LocalDateTime createdAt
) {}