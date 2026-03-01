package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.dto.TransactionRequest;
import com.restapi.store.vehicleregistration.dto.TransactionResponse;
import com.restapi.store.vehicleregistration.service.VehicleTransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/transactions")
@RequiredArgsConstructor
@Slf4j
public class VehicleTransactionController {

    private final VehicleTransactionService transactionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<TransactionResponse> processTransaction(@Valid @RequestBody TransactionRequest request) {
        log.info("Received request to process transaction");
        return CompletableFuture.supplyAsync(() -> transactionService.processTransaction(request));
    }

    @GetMapping("/history/{email}")
    public CompletableFuture<Page<TransactionResponse>> getTransactionHistory(@PathVariable String email, Pageable pageable) {
        log.info("Received request to fetch transaction history for user: {}", email);
        return CompletableFuture.supplyAsync(() -> transactionService.getTransactionHistory(email, pageable));
    }
}