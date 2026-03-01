package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.TransactionType;
import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.domain.VehicleStatus;
import com.restapi.store.vehicleregistration.domain.VehicleUser;
import com.restapi.store.vehicleregistration.domain.PaymentType;
import com.restapi.store.vehicleregistration.domain.VehicleTransaction;
import com.restapi.store.vehicleregistration.dto.TransactionRequest;
import com.restapi.store.vehicleregistration.dto.TransactionResponse;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import com.restapi.store.vehicleregistration.repository.VehicleTransactionRepository;
import com.restapi.store.vehicleregistration.repository.VehicleUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleTransactionServiceTest {

    @Mock
    private VehicleTransactionRepository transactionRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private VehicleUserRepository userRepository;

    @InjectMocks
    private VehicleTransactionService service;

    private Vehicle vehicle;
    private VehicleUser user;
    private TransactionRequest request;

    @BeforeEach
    void setUp() {
        user = VehicleUser.builder()
                .id(1L)
                .email("buyer@example.com")
                .build();

        vehicle = Vehicle.builder()
                .id(100L)
                .registrationNumber("XYZ999")
                .status(VehicleStatus.AVAILABLE)
                .build();

        request = TransactionRequest.builder()
                .vehicleId(100L)
                .userEmail("buyer@example.com")
                .transactionType(TransactionType.BUY)
                .amount(BigDecimal.valueOf(10000))
                .paymentMode(PaymentType.NET_BANKING)
                .build();
    }

    @Test
    void processTransaction_Buy_Success() {
        when(vehicleRepository.findById(100L)).thenReturn(Optional.of(vehicle));
        when(userRepository.findByEmail("buyer@example.com")).thenReturn(Optional.of(user));
        when(transactionRepository.save(any(VehicleTransaction.class))).thenAnswer(invocation -> {
            VehicleTransaction t = invocation.getArgument(0);
            t.setId(1L);
            return t;
        });

        TransactionResponse response = service.processTransaction(request);

        assertNotNull(response);
        assertEquals(VehicleStatus.SOLD, vehicle.getStatus());
        assertEquals(user, vehicle.getOwner());
        assertEquals(1L, response.id());
        verify(vehicleRepository, times(1)).save(vehicle);
    }
}