package com.restapi.store.vehicleregistration.dto;

import com.restapi.store.vehicleregistration.domain.Address;
import com.restapi.store.vehicleregistration.domain.BankingDetails;
import com.restapi.store.vehicleregistration.domain.PaymentType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
public record UserRegistrationRequest(
        @NotBlank(message = "First name is required")
        String firstName,
        String middleName,
        @NotBlank(message = "Last name is required")
        String lastName,
        
        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        
        @NotBlank(message = "Password is required")
        @Size(min = 8, message = "Password must be at least 8 characters long")
        String password,
        
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