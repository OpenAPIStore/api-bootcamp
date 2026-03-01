package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.TransactionType;
import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleTransaction;
import com.restapi.store.vehicleregistration.domain.VehicleUser;
import com.restapi.store.vehicleregistration.dto.TransactionRequest;
import com.restapi.store.vehicleregistration.dto.TransactionResponse;
import com.restapi.store.vehicleregistration.exception.ResourceNotFoundException;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import com.restapi.store.vehicleregistration.repository.VehicleTransactionRepository;
import com.restapi.store.vehicleregistration.repository.VehicleUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehicleTransactionService {

    private final VehicleTransactionRepository transactionRepository;
    private final VehicleRepository vehicleRepository;
    private final VehicleUserRepository userRepository;

    @Transactional
    public TransactionResponse processTransaction(TransactionRequest request) {
        log.info("Processing transaction of type {} for vehicle {}", request.transactionType(), request.vehicleId());

        Vehicle vehicle = vehicleRepository.findById(request.vehicleId())
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found with id: " + request.vehicleId()));

        VehicleUser user = userRepository.findByEmail(request.userEmail())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + request.userEmail()));

        if (request.transactionType() == TransactionType.BUY) {
            if (vehicle.getStatus() != VehicleStatus.AVAILABLE) {
                throw new IllegalStateException("Vehicle is not available for purchase");
            }
            vehicle.setStatus(VehicleStatus.SOLD);
            vehicle.setOwner(user); // User becomes the new owner
        } else if (request.transactionType() == TransactionType.SELL) {
            // User selling to the platform
            vehicle.setStatus(VehicleStatus.AVAILABLE);
            // In a real scenario, the platform becomes the owner. Here we might nullify the owner or leave it as history.
            // Let's set owner to null to represent platform inventory, or keep user if it's consignment. 
            // We'll set it to null for platform inventory.
            vehicle.setOwner(null); 
        }

        vehicleRepository.save(vehicle);

        VehicleTransaction transaction = VehicleTransaction.builder()
                .transactionType(request.transactionType())
                .vehicle(vehicle)
                .user(user)
                .amount(request.amount())
                .paymentMode(request.paymentMode())
                .build();

        VehicleTransaction savedTransaction = transactionRepository.save(transaction);

        return mapToResponse(savedTransaction);
    }

    @Transactional(readOnly = true)
    public Page<TransactionResponse> getTransactionHistory(String email, Pageable pageable) {
        log.info("Fetching transaction history for email: {}", email);
        return transactionRepository.findByUserEmail(email, pageable)
                .map(this::mapToResponse);
    }

    private TransactionResponse mapToResponse(VehicleTransaction transaction) {
        return TransactionResponse.builder()
                .id(transaction.getId())
                .vehicleId(transaction.getVehicle().getId())
                .vehicleRegistrationNumber(transaction.getVehicle().getRegistrationNumber())
                .userEmail(transaction.getUser().getEmail())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .paymentMode(transaction.getPaymentMode())
                .transactionDate(transaction.getTransactionDate())
                .build();
    }
}