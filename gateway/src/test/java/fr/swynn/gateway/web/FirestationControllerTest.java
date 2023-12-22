package fr.swynn.gateway.web;

import fr.swynn.gateway.core.GatewayFirestation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

class FirestationControllerTest {

    private FirestationController controller;

    @BeforeEach
    void setUp() {
        controller = new FirestationController();
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

    @Test
    void createFirestation_returnConflict_existingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1233 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new GatewayFirestation(firestationAddress, firestationStation);
        // WHEN we create a firestation
        controller.createFirestation(firestation);
        final var response = controller.createFirestation(firestation);
        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void updateFirestation_returnFirestation_existingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new GatewayFirestation(firestationAddress, firestationStation);

        // WHEN we create a firestation
        controller.createFirestation(firestation);
        final var response = controller.updateFirestation(firestation);

        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // AND the firestation is returned
        final var returnedFirestation = response.getBody();
        Assertions.assertNotNull(returnedFirestation);
        Assertions.assertEquals(firestationAddress, returnedFirestation.address());
        Assertions.assertEquals(firestationStation, returnedFirestation.station());
    }

    @Test
    void updateFirestation_returnNotFound_nonExistingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1233 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new GatewayFirestation(firestationAddress, firestationStation);

        // WHEN we create a firestation
        final var response = controller.updateFirestation(firestation);

        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteFirestation_returnFirestation_existingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new GatewayFirestation(firestationAddress, firestationStation);

        // WHEN we create a firestation
        controller.createFirestation(firestation);
        final var response = controller.deleteFirestation(firestation);

        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // AND the firestation is returned
        final var returnedFirestation = response.getBody();
        Assertions.assertNotNull(returnedFirestation);
        Assertions.assertEquals(firestationAddress, returnedFirestation.address());
        Assertions.assertEquals(firestationStation, returnedFirestation.station());
    }

    @Test
    void deleteFirestation_returnNotFound_nonExistingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1233 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new GatewayFirestation(firestationAddress, firestationStation);

        // WHEN we create a firestation
        final var response = controller.deleteFirestation(firestation);

        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
