package fr.swynn.launcher;

import fr.swynn.gateway.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GatewayToFirestationServiceTest {

    private Gateway center;

    @BeforeEach
    void setUp() {
        center = ConfiguredGatewayProxy.gateway();
    }

    @Test
    void getFirestationAddress_returnListOfString_defaultState() throws GatewayUnknownFirestation {
        // GIVEN a firestation service
        final var station = "2";
        // WHEN getting the firestation addresses
        final var addresses = center.getFirestationAddressByStationNumber(station);
        // THEN the addresses shouldn't be null
        Assertions.assertNotNull(addresses);
        Assertions.assertEquals(3, addresses.size());
    }

    @Test
    void getFirestationAddress_throwUnknownFirestation_nonExistingFirestation() {
        // GIVEN a firestation service
        final var station = "4";
        // WHEN getting the firestation addresses
        // THEN the firestation service should throw an exception
        Assertions.assertThrows(GatewayUnknownFirestation.class, () -> center.getFirestationAddressByStationNumber(station));
    }

    @Test
    void createFirestation_returnFirestation_nonExistingFirestation() throws GatewayFirestationAlreadyExist {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1509 Baylee St", "3");
        // WHEN creating the firestation
        final var createdFirestation = center.createFirestation(firestation);
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
        Assertions.assertThrows(GatewayFirestationAlreadyExist.class, () -> center.createFirestation(firestation));
    }

    @Test
    void updateFirestation_returnFirestation_existingFirestation() throws GatewayUnknownFirestation {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1 rue de la paix", "1");
        // WHEN creating the firestation
        final var createdFirestation = center.updateFirestation(firestation);
        // THEN the created firestation shouldn't be null
        Assertions.assertNotNull(createdFirestation);
    }

    @Test
    void updateFirestation_throwUnknownFirestation_nonExistingFirestation() {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1509 Baylee St", "3");
        // WHEN creating the firestation
        // THEN the firestation service should throw an exception
        Assertions.assertThrows(GatewayUnknownFirestation.class, () -> center.updateFirestation(firestation));
    }

    @Test
    void deleteFirestation_returnFirestation_existingFirestation() throws GatewayUnknownFirestation {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1 rue de la paix", "1");
        // WHEN creating the firestation
        final var createdFirestation = center.deleteFirestation(firestation);
        // THEN the created firestation shouldn't be null
        Assertions.assertNotNull(createdFirestation);
    }

    @Test
    void deleteFirestation_throwUnknownFirestation_nonExistingFirestation() {
        // GIVEN a firestation service
        // AND a station
        final var firestation = new GatewayFirestation("1509 Baylee St", "3");
        // WHEN creating the firestation
        // THEN the firestation service should throw an exception
        Assertions.assertThrows(GatewayUnknownFirestation.class, () -> center.deleteFirestation(firestation));
    }
}
