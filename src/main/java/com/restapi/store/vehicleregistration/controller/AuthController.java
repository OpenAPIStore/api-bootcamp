package com.restapi.store.vehicleregistration.controller;

import com.restapi.store.vehicleregistration.dto.UserRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.UserResponse;
import com.restapi.store.vehicleregistration.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public CompletableFuture<UserResponse> registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        log.info("Received request to register user: {}", request.email());
        return CompletableFuture.supplyAsync(() -> userService.registerUser(request));
    }
}