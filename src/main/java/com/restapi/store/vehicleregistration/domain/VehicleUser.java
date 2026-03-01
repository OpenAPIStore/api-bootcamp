package com.restapi.store.vehicleregistration.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "vehicle_users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String password; // Will store hashed password

    @Column(unique = true, nullable = false)
    private String email;

    private String phoneNumber;

    private String profileImageUrl;
    
    // Extra Demographics
    private String dateOfBirth;
    private String gender;
    private String occupation;
    private String nationality;
    private String governmentId; // e.g., SSN, PAN, Aadhar

    @Enumerated(EnumType.STRING)
    private PaymentType paymentType;

    private Boolean salaried;

    @Embedded
    private BankingDetails bankingDetails;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "comm_address_line1")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "comm_address_line2")),
            @AttributeOverride(name = "city", column = @Column(name = "comm_city")),
            @AttributeOverride(name = "state", column = @Column(name = "comm_state")),
            @AttributeOverride(name = "zip", column = @Column(name = "comm_zip"))
    })
    private Address communicationAddress;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "addressLine1", column = @Column(name = "ship_address_line1")),
            @AttributeOverride(name = "addressLine2", column = @Column(name = "ship_address_line2")),
            @AttributeOverride(name = "city", column = @Column(name = "ship_city")),
            @AttributeOverride(name = "state", column = @Column(name = "ship_state")),
            @AttributeOverride(name = "zip", column = @Column(name = "ship_zip"))
    })
    private Address shippingAddress;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<Vehicle> vehicles = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @ToString.Exclude
    private List<VehicleTransaction> transactions = new ArrayList<>();

    @ManyToMany
    @JoinTable(
        name = "user_saved_vehicles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "vehicle_id")
    )
    @Builder.Default
    @ToString.Exclude
    private List<Vehicle> savedVehicles = new ArrayList<>();

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}