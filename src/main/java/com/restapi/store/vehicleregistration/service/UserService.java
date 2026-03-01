package com.restapi.store.vehicleregistration.service;

import com.restapi.store.vehicleregistration.domain.VehicleUser;
import com.restapi.store.vehicleregistration.dto.UserRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.UserResponse;
import com.restapi.store.vehicleregistration.repository.VehicleUserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.restapi.store.vehicleregistration.domain.Vehicle;
import com.restapi.store.vehicleregistration.repository.VehicleRepository;
import com.restapi.store.vehicleregistration.exception.ResourceNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final VehicleUserRepository userRepository;
    private final VehicleRepository vehicleRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponse registerUser(UserRegistrationRequest request) {
        log.info("Registering new user with email: {}", request.email());

        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new IllegalStateException("Email already in use");
        }

        VehicleUser user = VehicleUser.builder()
                .firstName(request.firstName())
                .middleName(request.middleName())
                .lastName(request.lastName())
                .email(request.email())
                .password(passwordEncoder.encode(request.password()))
                .phoneNumber(request.phoneNumber())
                .profileImageUrl(request.profileImageUrl())
                .dateOfBirth(request.dateOfBirth())
                .gender(request.gender())
                .occupation(request.occupation())
                .nationality(request.nationality())
                .governmentId(request.governmentId())
                .paymentType(request.paymentType())
                .salaried(request.salaried())
                .bankingDetails(request.bankingDetails())
                .communicationAddress(request.communicationAddress())
                .shippingAddress(request.shippingAddress())
                .build();

        VehicleUser savedUser = userRepository.save(user);
        return mapToResponse(savedUser);
    }

    @Transactional
    public void addFavoriteVehicle(String email, Long vehicleId) {
        VehicleUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle not found"));
        
        if (!user.getSavedVehicles().contains(vehicle)) {
            user.getSavedVehicles().add(vehicle);
            userRepository.save(user);
        }
    }

    @Transactional
    public void removeFavoriteVehicle(String email, Long vehicleId) {
        VehicleUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.getSavedVehicles().removeIf(v -> v.getId().equals(vehicleId));
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<Long> getFavoriteVehicleIds(String email) {
        VehicleUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return user.getSavedVehicles().stream().map(Vehicle::getId).collect(Collectors.toList());
    }

    public UserResponse mapToResponse(VehicleUser user) {
        if (user == null) return null;
        return UserResponse.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .profileImageUrl(user.getProfileImageUrl())
                .dateOfBirth(user.getDateOfBirth())
                .gender(user.getGender())
                .occupation(user.getOccupation())
                .nationality(user.getNationality())
                .governmentId(user.getGovernmentId())
                .paymentType(user.getPaymentType())
                .salaried(user.getSalaried())
                .bankingDetails(user.getBankingDetails())
                .communicationAddress(user.getCommunicationAddress())
                .shippingAddress(user.getShippingAddress())
                .build();
    }
}