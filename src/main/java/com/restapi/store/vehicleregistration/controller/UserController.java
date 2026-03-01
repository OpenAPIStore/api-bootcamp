package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    @PostMapping("/{email}/favorites/{vehicleId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<Void> addFavoriteVehicle(@PathVariable String email, @PathVariable Long vehicleId) {
        log.info("Adding vehicle {} to favorites for user {}", vehicleId, email);
        return CompletableFuture.runAsync(() -> userService.addFavoriteVehicle(email, vehicleId));
    }

    @DeleteMapping("/{email}/favorites/{vehicleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public CompletableFuture<Void> removeFavoriteVehicle(@PathVariable String email, @PathVariable Long vehicleId) {
        log.info("Removing vehicle {} from favorites for user {}", vehicleId, email);
        return CompletableFuture.runAsync(() -> userService.removeFavoriteVehicle(email, vehicleId));
    }

    @GetMapping("/{email}/favorites")
    public CompletableFuture<List<Long>> getFavoriteVehicleIds(@PathVariable String email) {
        log.info("Fetching favorite vehicles for user {}", email);
        return CompletableFuture.supplyAsync(() -> userService.getFavoriteVehicleIds(email));
    }
}