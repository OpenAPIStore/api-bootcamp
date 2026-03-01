package com.restapi.store.vehicleregistration.dto;

import com.restapi.store.vehicleregistration.domain.Address;
import com.restapi.store.vehicleregistration.domain.BankingDetails;
import com.restapi.store.vehicleregistration.domain.PaymentType;
import lombok.Builder;

@Builder
public record UserResponse(
        Long id,
        String firstName,
        String middleName,
        String lastName,
        String email,
        String phoneNumber,
        String profileImageUrl,
        String dateOfBirth,
        String gender,
        String occupation,
        String nationality,
        String governmentId,
        PaymentType paymentType,
        Boolean salaried,
        BankingDetails bankingDetails,
        Address communicationAddress,
        Address shippingAddress
) {}