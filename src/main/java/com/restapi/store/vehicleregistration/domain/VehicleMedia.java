package com.restapi.store.vehicleregistration.domain;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "vehicle_media")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VehicleMedia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String fileType;

    @Lob
    private byte[] data;

    @CreationTimestamp
    private LocalDateTime uploadedAt;
}