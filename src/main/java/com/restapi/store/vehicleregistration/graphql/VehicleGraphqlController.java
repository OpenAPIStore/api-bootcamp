package com.restapi.store.vehicleregistration.graphql;

import com.restapi.store.vehicleregistration.dto.TransactionRequest;
import com.restapi.store.vehicleregistration.dto.TransactionResponse;
import com.restapi.store.vehicleregistration.dto.VehicleRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.VehicleResponse;
import com.restapi.store.vehicleregistration.service.InventoryService;
import com.restapi.store.vehicleregistration.service.VehicleRegistrationService;
import com.restapi.store.vehicleregistration.service.VehicleTransactionService;
import com.restapi.store.vehicleregistration.service.MediaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;
import jakarta.validation.Valid;

import com.restapi.store.vehicleregistration.dto.ReviewRequest;
import com.restapi.store.vehicleregistration.dto.ReviewResponse;
import com.restapi.store.vehicleregistration.service.ReviewService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Controller
@RequiredArgsConstructor
@Slf4j
public class VehicleGraphqlController {

    private final VehicleRegistrationService vehicleRegistrationService;
    private final InventoryService inventoryService;
    private final VehicleTransactionService transactionService;
    private final MediaService mediaService;
    private final ReviewService reviewService;

    @QueryMapping
    public CompletableFuture<List<ReviewResponse>> getReviewsForVehicle(@Argument Long vehicleId, @Argument int page, @Argument int size) {
        log.info("GraphQL Query: getReviewsForVehicle for vehicle {}", vehicleId);
        return CompletableFuture.supplyAsync(() -> 
                reviewService.getReviewsForVehicle(vehicleId, PageRequest.of(page, size)).getContent());
    }

    @MutationMapping
    public CompletableFuture<ReviewResponse> addReview(@Valid @Argument("request") ReviewRequest request) {
        log.info("GraphQL Mutation: addReview");
        return CompletableFuture.supplyAsync(() -> reviewService.addReview(request));
    }

    @MutationMapping
    public CompletableFuture<Boolean> deleteReview(@Argument Long id) {
        log.info("GraphQL Mutation: deleteReview for id {}", id);
        return CompletableFuture.supplyAsync(() -> {
            reviewService.deleteReview(id);
            return true;
        });
    }

    @MutationMapping
    public CompletableFuture<String> uploadMedia(@Argument MultipartFile file) {
        log.info("GraphQL Mutation: uploadMedia");
        return CompletableFuture.supplyAsync(() -> {
            Long mediaId = mediaService.uploadMedia(file);
            return String.valueOf(mediaId);
        });
    }

    @QueryMapping
    public CompletableFuture<VehicleResponse> getVehicleById(@Argument Long id) {
        log.info("GraphQL Query: getVehicleById for id: {}", id);
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.getVehicleById(id));
    }

    @QueryMapping
    public CompletableFuture<List<VehicleResponse>> getAllVehicles(@Argument int page, @Argument int size) {
        log.info("GraphQL Query: getAllVehicles page: {}, size: {}", page, size);
        return CompletableFuture.supplyAsync(() -> 
                vehicleRegistrationService.getAllVehicles(PageRequest.of(page, size)).getContent());
    }

    @QueryMapping
    public CompletableFuture<List<VehicleResponse>> searchVehicles(
            @Argument Double minPrice, @Argument Double maxPrice, @Argument String brand,
            @Argument Integer minYear, @Argument Integer maxYear, @Argument String condition,
            @Argument Integer maxMileage, @Argument int page, @Argument int size) {
        log.info("GraphQL Query: searchVehicles");
        java.math.BigDecimal min = minPrice != null ? java.math.BigDecimal.valueOf(minPrice) : null;
        java.math.BigDecimal max = maxPrice != null ? java.math.BigDecimal.valueOf(maxPrice) : null;
        com.restapi.store.vehicleregistration.domain.VehicleCondition cond = 
            (condition != null && !condition.isBlank()) ? com.restapi.store.vehicleregistration.domain.VehicleCondition.valueOf(condition.toUpperCase()) : null;

        return CompletableFuture.supplyAsync(() -> 
                vehicleRegistrationService.searchVehicles(min, max, brand, minYear, maxYear, cond, maxMileage, PageRequest.of(page, size)).getContent());
    }

    @QueryMapping
    public CompletableFuture<Long> getTotalAvailableVehicles(@Argument String type) {
        log.info("GraphQL Query: getTotalAvailableVehicles with type: {}", type);
        com.restapi.store.vehicleregistration.domain.VehicleType vehicleType = 
            (type != null && !type.isBlank()) ? com.restapi.store.vehicleregistration.domain.VehicleType.valueOf(type.toUpperCase()) : null;
        return CompletableFuture.supplyAsync(() -> inventoryService.getTotalAvailableVehicles(vehicleType));
    }

    @QueryMapping
    public CompletableFuture<List<TransactionResponse>> getTransactionHistory(@Argument String email, @Argument int page, @Argument int size) {
        log.info("GraphQL Query: getTransactionHistory for email: {}", email);
        return CompletableFuture.supplyAsync(() -> 
                transactionService.getTransactionHistory(email, PageRequest.of(page, size)).getContent());
    }

    @MutationMapping
    public CompletableFuture<VehicleResponse> registerVehicle(@Valid @Argument("request") VehicleRegistrationRequest request) {
        log.info("GraphQL Mutation: registerVehicle");
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.registerVehicle(request));
    }

    @MutationMapping
    public CompletableFuture<VehicleResponse> updateVehicle(@Argument Long id, @Valid @Argument("request") VehicleRegistrationRequest request) {
        log.info("GraphQL Mutation: updateVehicle for id: {}", id);
        return CompletableFuture.supplyAsync(() -> vehicleRegistrationService.updateVehicle(id, request));
    }

    @MutationMapping
    public CompletableFuture<Boolean> deleteVehicle(@Argument Long id) {
        log.info("GraphQL Mutation: deleteVehicle for id: {}", id);
        return CompletableFuture.supplyAsync(() -> {
            vehicleRegistrationService.deleteVehicle(id);
            return true;
        });
    }

    @MutationMapping
    public CompletableFuture<TransactionResponse> processTransaction(@Valid @Argument("request") TransactionRequest request) {
        log.info("GraphQL Mutation: processTransaction");
        return CompletableFuture.supplyAsync(() -> transactionService.processTransaction(request));
    }
}