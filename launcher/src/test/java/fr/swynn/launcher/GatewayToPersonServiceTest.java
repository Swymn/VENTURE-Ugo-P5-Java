package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPersonAlreadyExist;
import fr.swynn.gateway.core.GatewayPersona;
import fr.swynn.gateway.core.GatewayUnknownPerson;
import fr.swynn.gateway.core.PersonServiceProxy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GatewayToPersonServiceTest {

    private PersonServiceProxy service;

    @BeforeEach
    void setUp() {
        service = new GatewayToPersonService();
    }

    @Test
    void getPersonasByAddress_returnListOfPersona_defaultState() {
        // GIVEN a persona service
        final var address = "1509 Culver St";
        // WHEN getting the personas by address
        final var personas = service.getPersonByAddress(address);
        // THEN the personas shouldn't be null
        Assertions.assertNotNull(personas);
        Assertions.assertEquals(2, personas.size());
    }

    @Test
    void deletePersona_returnPersona_existingPersona() throws GatewayUnknownPerson {
        // GIVEN a persona service
        // AND a persona
        final var persona = new GatewayPersona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com");
        // WHEN deleting the persona
        final var deletedPersona = service.deletePerson(persona);
        // THEN the deleted persona shouldn't be null
        Assertions.assertNotNull(deletedPersona);
    }

    @Test
    void deletePersona_throwUnknownPerson_nonExistingPersona() {
        // GIVEN a persona service
        // AND a persona
        final var persona = new GatewayPersona("John", "Boyd", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.boyd@mail.com");
        // WHEN deleting the persona
        // THEN the persona service should throw an exception
        Assertions.assertThrows(GatewayUnknownPerson.class, () -> service.deletePerson(persona));
    }

    @Test
    void updatePersona_returnPersona_existingPersona() throws GatewayUnknownPerson {
        // GIVEN a persona service
        // AND a persona
        final var persona = new GatewayPersona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com");
        // WHEN updating the persona
        final var updatedPersona = service.updatePerson(persona);
        // THEN the updated persona shouldn't be null
        Assertions.assertNotNull(updatedPersona);
    }

    @Test
    void updatePersona_throwUnknownPerson_nonExistingPersona() {
        // GIVEN a persona service
        // AND a persona
        final var persona = new GatewayPersona("John", "Boyd", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.boyd@mail.com");
        // WHEN updating the persona
        // THEN the persona service should throw an exception
        Assertions.assertThrows(GatewayUnknownPerson.class, () -> service.updatePerson(persona));
    }

    @Test
    void createPersona_returnPersona_nonExistingPersona() throws GatewayPersonAlreadyExist {
        // GIVEN a persona service
        // AND a persona
        final var persona = new GatewayPersona("John", "Boyd", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.body@mail.com");
        // WHEN creating the persona
        final var createdPersona = service.createPerson(persona);
        // THEN the created persona shouldn't be null
        Assertions.assertNotNull(createdPersona);
    }

    @Test
    void createPersona_throwPersonAlreadyExist_existingPersona() {
        // GIVEN a persona service
        // AND a persona
        final var persona = new GatewayPersona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com");
        // WHEN creating the persona
        // THEN the persona service should throw an exception
        Assertions.assertThrows(GatewayPersonAlreadyExist.class, () -> service.createPerson(persona));
    }
}
