package com.restapi.store.vehicleregistration.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicles")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private VehicleType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private VehicleStatus status = VehicleStatus.AVAILABLE;

    @Enumerated(EnumType.STRING)
    private VehicleCondition vehicleCondition;

    private String brand;
    private String make;
    private String model;
    private Integer manufactureYear;
    
    @Column(unique = true, nullable = false)
    private String registrationNumber;

    @Column(unique = true)
    private String vin;

    private Integer mileage; // Odometer reading
    private String bodyType; // Sedan, SUV, Coupe, etc.

    private BigDecimal cost;

    private Integer engineCapacity; // in cc
    private Integer power; // in BHP

    @Enumerated(EnumType.STRING)
    private FuelType fuelType;

    @Embedded
    private VehicleFeatures features;

    @ElementCollection
    @CollectionTable(name = "vehicle_images", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Column(name = "image_url")
    @Builder.Default
    private List<String> images = new ArrayList<>();

    @ElementCollection(targetClass = PaymentType.class)
    @CollectionTable(name = "vehicle_payment_modes", joinColumns = @JoinColumn(name = "vehicle_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_mode")
    @Builder.Default
    private List<PaymentType> availablePaymentModes = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id") // changed from user_id to better reflect current owner
    @ToString.Exclude
    private VehicleUser owner;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}