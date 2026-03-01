package com.restapi.store.vehicleregistration.dto;

import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ReviewResponse(
        Long id,
        Long vehicleId,
        String authorName,
        String authorProfileImageUrl,
        Integer rating,
        String comment,
        LocalDateTime createdAt
) {}