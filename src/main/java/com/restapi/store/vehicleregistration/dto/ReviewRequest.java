package com.restapi.store.vehicleregistration.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record ReviewRequest(
        @NotNull(message = "Vehicle ID is required")
        Long vehicleId,
        
        @NotBlank(message = "Author email is required")
        String authorEmail,
        
        @NotNull(message = "Rating is required")
        @Min(value = 1, message = "Rating must be at least 1")
        @Max(value = 5, message = "Rating cannot be greater than 5")
        Integer rating,
        
        String comment
) {}