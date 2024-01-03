package fr.swynn.persona.data;

import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.impl.PersonAlreadyExist;
import fr.swynn.persona.impl.SNPersonaService;
import fr.swynn.persona.impl.UnknownPerson;
import fr.swynn.persona.model.Persona;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonaServiceTest {

    private PersonaService personaService;

    @BeforeEach
    void setUp() {
        personaService = new SNPersonaService();
    }

    @Test
    void deletePersona_returnPersona_existingPersona() throws UnknownPerson {
        // GIVEN a persona service
        final var persona = new Persona("John", "Doe", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.doe@mail.com");
        // WHEN we get a persona
        final var deletedPersona = personaService.deletePersona(persona);
        // THEN the persona is returned
        Assertions.assertNotNull(deletedPersona);
    }

    @Test
    void deletePersona_throwUnknownPerson_nonExistingPersona() {
        // GIVEN a persona service
        final var persona = new Persona("John", "Boyd", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.boyd@mail.com");
        // WHEN we get a persona
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownPerson.class, () -> personaService.deletePersona(persona));
    }

    @Test
    void updatePersona_returnPersona_existingPersona() throws UnknownPerson {
        // GIVEN a persona service
        // AND a person
        final var persona = new Persona("John", "Doe", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.doe03213@mail.com");
        // WHEN we update the person
        final var updatedPersona = personaService.updatePersona(persona);
        // THEN the updated person is returned
        Assertions.assertNotNull(updatedPersona);
        Assertions.assertEquals(persona.firstName(), updatedPersona.firstName());
        Assertions.assertEquals(persona.lastName(), updatedPersona.lastName());
        Assertions.assertEquals(persona.address(), updatedPersona.address());
        Assertions.assertEquals(persona.city(), updatedPersona.city());
        Assertions.assertEquals(persona.zip(), updatedPersona.zip());
        Assertions.assertEquals(persona.phone(), updatedPersona.phone());
        Assertions.assertEquals(persona.email(), updatedPersona.email());
    }

    @Test
    void updatePersona_throwUnknownPerson_nonExistingPersona() {
        // GIVEN a persona service
        // AND a person
        final var persona = new Persona("John", "Boyd", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.boyd@mail.com");
        // WHEN we update the person
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownPerson.class, () -> personaService.updatePersona(persona));
    }

    @Test
    void createPersona_returnPersona_newPersona() throws PersonAlreadyExist {
        // GIVEN a persona service
        // AND a person
        final var persona = new Persona("John", "Boyd", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.boyd@mail.com");
        // WHEN we create the person
        final var createdPersona = personaService.createPersona(persona);
        // THEN the created person is returned
        Assertions.assertNotNull(createdPersona);
        Assertions.assertEquals(persona.firstName(), createdPersona.firstName());
        Assertions.assertEquals(persona.lastName(), createdPersona.lastName());
        Assertions.assertEquals(persona.address(), createdPersona.address());
        Assertions.assertEquals(persona.city(), createdPersona.city());
        Assertions.assertEquals(persona.zip(), createdPersona.zip());
        Assertions.assertEquals(persona.phone(), createdPersona.phone());
        Assertions.assertEquals(persona.email(), createdPersona.email());
    }

    @Test
    void createPersona_throwPersonAlreadyExist_existingPersona() {
        // GIVEN a persona service
        // AND a person
        final var persona = new Persona("John", "Doe", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.doe@mail.com");
        // WHEN we create the person
        // THEN an exception is thrown
        Assertions.assertThrows(PersonAlreadyExist.class, () -> personaService.createPersona(persona));
    }

    @Test
    void getCommunityEmail_returnEmails_existingCity() {
        // GIVEN a persona service
        // AND a city
        final var city = "Culver";
        // WHEN we get the emails
        final var emails = personaService.getCommunityEmail(city);
        // THEN the emails are returned
        Assertions.assertNotNull(emails);
        Assertions.assertFalse(emails.isEmpty());
    }

    @Test
    void getCommunityEmail_returnEmptyList_nonExistingCity() {
        // GIVEN a persona service
        // AND a city
        final var city = "Paris";
        // WHEN we get the emails
        final var emails = personaService.getCommunityEmail(city);
        // THEN the emails are returned
        Assertions.assertNotNull(emails);
        Assertions.assertTrue(emails.isEmpty());
    }
}
