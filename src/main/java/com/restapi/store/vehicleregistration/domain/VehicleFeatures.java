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
public class VehicleFeatures {
    private Boolean hasAirbags;
    private Boolean hasAbs;
    private Boolean hasSunroof;
    private Boolean hasTouchscreen;
    private Boolean hasCruiseControl;
    private Boolean hasParkingSensors;
    private Boolean hasRearviewCamera;
    private Boolean hasAlloyWheels;
    private Boolean hasLeatherSeats;
    private Boolean hasKeylessEntry;
    private Boolean hasPushButtonStart;
    private Boolean hasClimateControl;
    private Boolean hasBluetooth;
    private Boolean hasNavigation;
    private Boolean hasHeatedSeats;
    private Boolean hasLedHeadlights;
    private Boolean hasPowerWindows;
    private Boolean hasPowerSteering;
    private Boolean hasTractionControl;
    private Boolean hasHillAssist;
    
    private String transmissionType; // Manual, Automatic, CVT, etc.
    private Integer seatingCapacity;
    private Integer doors;
}