package com.restapi.store.vehicleregistration.dto;

import com.restapi.store.vehicleregistration.domain.PaymentType;
import com.restapi.store.vehicleregistration.domain.TransactionType;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
public record TransactionResponse(
        Long id,
        Long vehicleId,
        String vehicleRegistrationNumber,
        String userEmail,
        TransactionType transactionType,
        BigDecimal amount,
        PaymentType paymentMode,
        LocalDateTime transactionDate
) {}