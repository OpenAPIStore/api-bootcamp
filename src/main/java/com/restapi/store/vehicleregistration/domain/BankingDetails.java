package com.restapi.store.vehicleregistration.domain;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankingDetails {
    private String bankName;
    private String accountNumber;
    private String routingNumber; // IFSC / Routing
    private String accountHolderName;
    private String accountType; // Savings, Checking, etc.
}