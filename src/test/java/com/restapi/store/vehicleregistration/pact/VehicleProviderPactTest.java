package com.restapi.store.vehicleregistration.pact;

import au.com.dius.pact.provider.junit5.HttpTestTarget;
import au.com.dius.pact.provider.junit5.PactVerificationContext;
import au.com.dius.pact.provider.junit5.PactVerificationInvocationContextProvider;
import au.com.dius.pact.provider.junitsupport.Provider;
import au.com.dius.pact.provider.junitsupport.State;
import au.com.dius.pact.provider.junitsupport.loader.PactFolder;
import com.restapi.store.vehicleregistration.VehicleRegistrationApplication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestTemplate;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

@Provider("VehicleRegistrationService")
@PactFolder("pacts")
@SpringBootTest(classes = VehicleRegistrationApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VehicleProviderPactTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void before(PactVerificationContext context) {
        if (context != null) {
            context.setTarget(new HttpTestTarget("localhost", port));
        }
    }

    @TestTemplate
    @ExtendWith(PactVerificationInvocationContextProvider.class)
    void pactVerificationTestTemplate(PactVerificationContext context) {
        if (context != null) {
            context.verifyInteraction();
        }
    }

    @State("A request to register a vehicle")
    public void setupRegisterVehicleState() {
        // Mock or setup data for this state
    }
    
    @State("A request to fetch a vehicle")
    public void setupGetVehicleState() {
        // Mock or setup data for this state
    }

    @State("A request to fetch inventory total")
    public void setupInventoryTotalState() {
        // Mock or setup data for this state
    }

    @State("A request to process a transaction")
    public void setupProcessTransactionState() {
        // Mock or setup data for this state
    }
}