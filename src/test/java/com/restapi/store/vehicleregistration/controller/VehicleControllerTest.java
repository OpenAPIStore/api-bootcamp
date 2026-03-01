package com.restapi.store.vehicleregistration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restapi.store.vehicleregistration.domain.VehicleType;
import com.restapi.store.vehicleregistration.dto.UserResponse;
import com.restapi.store.vehicleregistration.dto.VehicleRegistrationRequest;
import com.restapi.store.vehicleregistration.dto.VehicleResponse;
import com.restapi.store.vehicleregistration.service.VehicleRegistrationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
@AutoConfigureMockMvc
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private VehicleRegistrationService service;

    @Test
    @WithMockUser
    void registerVehicle() throws Exception {
        VehicleRegistrationRequest request = VehicleRegistrationRequest.builder()
                .firstName("Jane")
                .lastName("Doe")
                .email("jane@example.com")
                .vehicleType(VehicleType.BIKE)
                .brand("Honda")
                .registrationNumber("BIKE123")
                .cost(BigDecimal.valueOf(1000))
                .build();

        VehicleResponse response = VehicleResponse.builder()
                .id(1L)
                .type(VehicleType.BIKE)
                .registrationNumber("BIKE123")
                .owner(UserResponse.builder().email("jane@example.com").build())
                .build();

        when(service.registerVehicle(any())).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(post("/api/v1/vehicles")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.registrationNumber").value("BIKE123"));
    }

    @Test
    @WithMockUser
    void getVehicleById() throws Exception {
        VehicleResponse response = VehicleResponse.builder()
                .id(1L)
                .type(VehicleType.CAR)
                .registrationNumber("CAR123")
                .owner(UserResponse.builder().email("test@example.com").build())
                .build();

        when(service.getVehicleById(1L)).thenReturn(response);

        MvcResult mvcResult = mockMvc.perform(get("/api/v1/vehicles/1"))
                .andExpect(request().asyncStarted())
                .andReturn();

        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.registrationNumber").value("CAR123"));
    }
}