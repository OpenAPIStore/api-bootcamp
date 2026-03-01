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
public class Address {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String zip;
}