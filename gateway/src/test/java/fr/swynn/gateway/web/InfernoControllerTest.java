package fr.swynn.gateway.web;

import fr.swynn.gateway.core.GatewayFirestation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class InfernoControllerTest {

    private InfernoController controller;

    @BeforeEach
    void setUp() {
        controller = new InfernoController();
    }

    @Test
    void createFirestation_returnFirestation_existingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new GatewayFirestation(firestationAddress, firestationStation);
        // WHEN we create a firestation
        final var response = controller.createFirestation(firestation);
        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // AND the firestation is returned
        final var returnedFirestation = response.getBody();
        Assertions.assertNotNull(returnedFirestation);
        Assertions.assertEquals(firestationAddress, returnedFirestation.address());
        Assertions.assertEquals(firestationStation, returnedFirestation.station());
    }
}
