package com.restapi.store.vehicleregistration.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_transactions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TransactionType transactionType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "vehicle_id", nullable = false)
    @ToString.Exclude
    private Vehicle vehicle;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private VehicleUser user;

    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private PaymentType paymentMode;

    @CreationTimestamp
    private LocalDateTime transactionDate;
}