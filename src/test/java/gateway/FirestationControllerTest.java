package gateway;

import fr.swynn.gateway.FirestationController;
import fr.swynn.model.Firestation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@SpringBootTest(classes = FirestationController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class FirestationControllerTest {

    @Autowired
    private FirestationController controller;

    @BeforeEach
    void setUp() {
        controller = new FirestationController();
    }

    @Test
    void getPersonByStationNumber_returnPersonList_existingFirestation() {
        // GIVEN an inferno controller
        final var firestationStation = "3";

        // WHEN we get person by station number
        final var response = controller.getPersonByStationNumber(firestationStation);

        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // AND the list of the persons is returned
        final var returnedPersons = response.getBody();
        Assertions.assertNotNull(returnedPersons);
        Assertions.assertEquals(3, returnedPersons.citizens().size());
    }

    @Test
    void getPersonByStationNumber_returnNotFound_nonExistingFirestation() {
        // GIVEN an inferno controller
        final var firestationStation = "-10";

        // WHEN we get person by station number
        final var response = controller.getPersonByStationNumber(firestationStation);

        // THEN we receive an not found response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createFirestation_returnFirestation_existingFirestation() {
        // GIVEN an inferno controller
        final var firestationAddress = "1 rue de la paix";
        final var firestationStation = "1";

        final var firestation = new Firestation(firestationAddress, firestationStation);
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

        final var firestation = new Firestation(firestationAddress, firestationStation);
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

        final var firestation = new Firestation(firestationAddress, firestationStation);

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

        final var firestation = new Firestation(firestationAddress, firestationStation);

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

        final var firestation = new Firestation(firestationAddress, firestationStation);

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

        final var firestation = new Firestation(firestationAddress, firestationStation);

        // WHEN we create a firestation
        final var response = controller.deleteFirestation(firestation);

        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
