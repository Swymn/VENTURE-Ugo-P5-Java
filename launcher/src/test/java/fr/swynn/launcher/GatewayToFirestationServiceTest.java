package fr.swynn.launcher;

import fr.swynn.firestation.data.FirestationService;
import fr.swynn.firestation.model.Firestation;
import fr.swynn.gateway.core.FirestationServiceProxy;
import fr.swynn.gateway.core.GatewayFirestation;
import fr.swynn.gateway.core.GatewayFirestationAlreadyExist;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GatewayToFirestationServiceTest {

    private FirestationServiceProxy service;

    @BeforeEach
    void setUp() {
        service = new GatewayToFirestationService();
    }

    @Test
    void createFirestation_returnFirestation_nonExistingFirestation() throws GatewayFirestationAlreadyExist {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1509 Baylee St", "3");
        // WHEN creating the firestation
        final var createdFirestation = service.createFirestation(firestation);
        // THEN the created firestation shouldn't be null
        Assertions.assertNotNull(createdFirestation);
    }

    @Test
    void createFirestation_throwFirestationAlreadyExist_existingFirestation() {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1 rue de la paix", "1");
        // WHEN creating the firestation
        // THEN the firestation service should throw an exception
        Assertions.assertThrows(GatewayFirestationAlreadyExist.class, () -> service.createFirestation(firestation));
    }
}
