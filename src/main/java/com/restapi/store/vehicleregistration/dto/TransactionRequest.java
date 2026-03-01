package com.restapi.store.vehicleregistration.dto;

import com.restapi.store.vehicleregistration.domain.PaymentType;
import com.restapi.store.vehicleregistration.domain.TransactionType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record TransactionRequest(
        @NotNull(message = "Vehicle ID is required")
        Long vehicleId,
        @NotBlank(message = "User email is required")
        @Email(message = "User email should be valid")
        String userEmail, // Buyer or Seller email
        @NotNull(message = "Transaction type is required")
        TransactionType transactionType, // BUY (User buys from platform) or SELL (User sells to platform)
        @NotNull(message = "Amount is required")
        @Positive(message = "Amount must be positive")
        BigDecimal amount,
        @NotNull(message = "Payment mode is required")
        PaymentType paymentMode
) {}