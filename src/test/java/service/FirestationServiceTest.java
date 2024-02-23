package service;

import fr.swynn.exception.FirestationAlreadyExist;
import fr.swynn.service.FirestationService;
import fr.swynn.exception.UnknownFirestation;
import fr.swynn.model.Firestation;
import fr.swynn.service.impl.SafetyNetFirestationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirestationServiceTest {

    private FirestationService firestationService;

    @BeforeEach
    void setUp() {
        firestationService = new SafetyNetFirestationService();
    }

    @Test
    void deleteStation_shouldDeleteStation_existingStation() {
        // GIVEN a firestation service and a firestation
        final var address = "1509 Culver St";
        final var station = "3";
        final var firestation = new Firestation(address, station);

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertDoesNotThrow(() -> firestationService.deleteFirestation(firestation));
    }

    @Test
    void deleteStation_throwException_nonExistingStation() {
        // GIVEN a firestation service and a firestation
        final var address = "random address";
        final var station = "3";
        final var firestation = new Firestation(address, station);

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> firestationService.deleteFirestation(firestation));
    }

    @Test
    void updateStation_shouldUpdateStation_existingStation() {
        // GIVEN a firestation service and a firestation
        final var address = "1509 Culver St";
        final var station = "10";
        final var firestation = new Firestation(address, station);

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertDoesNotThrow(() -> firestationService.updateFirestation(firestation));
    }

    @Test
    void updateStation_throwException_nonExistingStation() {
        // GIVEN a firestation service and a firestation
        final var address = "random address";
        final var station = "10";
        final var firestation = new Firestation(address, station);

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> firestationService.updateFirestation(firestation));
    }

    @Test
    void createStation_shouldCreateStation_existingStation() {
        // GIVEN a firestation service and a firestation
        final var address = "14 rue de la paix";
        final var station = "6";
        final var firestation = new Firestation(address, station);

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertDoesNotThrow(() -> firestationService.createFirestation(firestation));
    }

    @Test
    void createStation_throwException_nonExistingStation() {
        // GIVEN a firestation service and a firestation
        final var address = "1509 Culver St";
        final var station = "3";
        final var firestation = new Firestation(address, station);

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertThrows(FirestationAlreadyExist.class, () -> firestationService.createFirestation(firestation));
    }

    @Test
    void getFirestationAddressByStationNumber_shouldReturnAddressList_existingStation() throws UnknownFirestation {
        // GIVEN a firestation service and a firestation
        final var station = "3";

        // WHEN deleting the firestation
        final var addresses = firestationService.getFirestationAddressByStationNumber(station);

        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertEquals(4, addresses.size());
    }

    @Test
    void getFirestationAddressByStationNumber_throwException_nonExistingStation() {
        // GIVEN a firestation service and a firestation
        final var station = "100";

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> firestationService.getFirestationAddressByStationNumber(station));
    }

    @Test
    void getFirestationNumbverByAddress_shouldReturnStationNumber_existingAddress() throws UnknownFirestation {
        // GIVEN a firestation service and a firestation
        final var address = "1509 Culver St";

        // WHEN deleting the firestation
        final var station = firestationService.getFirestationNumberByAddress(address);

        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertEquals("3", station);
    }

    @Test
    void getFirestationNumbverByAddress_throwException_nonExistingAddress() {
        // GIVEN a firestation service and a firestation
        final var address = "random address";

        // WHEN deleting the firestation
        // THEN the firestation is deleted, no exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> firestationService.getFirestationNumberByAddress(address));
    }
}
