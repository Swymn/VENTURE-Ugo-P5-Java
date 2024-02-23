package service;

import fr.swynn.exception.PersonAlreadyExist;
import fr.swynn.service.PersonService;
import fr.swynn.exception.UnknownPerson;
import fr.swynn.model.Person;
import fr.swynn.service.impl.SafetyNetPersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonServiceTest {

    private PersonService personaService;

    @BeforeEach
    void setUp() {
        personaService = new SafetyNetPersonService();
    }

    @Test
    void deletePerson_returnPerson_existingPerson() throws UnknownPerson {
        // GIVEN a persona service
        final var persona = new Person("John", "Doe", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.doe@mail.com");
        // WHEN we get a persona
        final var deletedPerson = personaService.deletePerson(persona);
        // THEN the persona is returned
        Assertions.assertNotNull(deletedPerson);
    }

    @Test
    void deletePerson_throwUnknownPerson_nonExistingPerson() {
        // GIVEN a persona service
        final var persona = new Person("John", "Boyd", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.boyd@mail.com");
        // WHEN we get a persona
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownPerson.class, () -> personaService.deletePerson(persona));
    }

    @Test
    void updatePerson_returnPerson_existingPerson() throws UnknownPerson {
        // GIVEN a persona service
        // AND a person
        final var persona = new Person("John", "Doe", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.doe03213@mail.com");
        // WHEN we update the person
        final var updatedPerson = personaService.updatePerson(persona);
        // THEN the updated person is returned
        Assertions.assertNotNull(updatedPerson);
        Assertions.assertEquals(persona.firstName(), updatedPerson.firstName());
        Assertions.assertEquals(persona.lastName(), updatedPerson.lastName());
        Assertions.assertEquals(persona.address(), updatedPerson.address());
        Assertions.assertEquals(persona.city(), updatedPerson.city());
        Assertions.assertEquals(persona.zip(), updatedPerson.zip());
        Assertions.assertEquals(persona.phone(), updatedPerson.phone());
        Assertions.assertEquals(persona.email(), updatedPerson.email());
    }

    @Test
    void updatePerson_throwUnknownPerson_nonExistingPerson() {
        // GIVEN a persona service
        // AND a person
        final var persona = new Person("John", "Boyd", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.boyd@mail.com");
        // WHEN we update the person
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownPerson.class, () -> personaService.updatePerson(persona));
    }

    @Test
    void createPerson_returnPerson_newPerson() throws PersonAlreadyExist {
        // GIVEN a persona service
        // AND a person
        final var persona = new Person("John", "Boyd", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.boyd@mail.com");
        // WHEN we create the person
        final var createdPerson = personaService.createPerson(persona);
        // THEN the created person is returned
        Assertions.assertNotNull(createdPerson);
        Assertions.assertEquals(persona.firstName(), createdPerson.firstName());
        Assertions.assertEquals(persona.lastName(), createdPerson.lastName());
        Assertions.assertEquals(persona.address(), createdPerson.address());
        Assertions.assertEquals(persona.city(), createdPerson.city());
        Assertions.assertEquals(persona.zip(), createdPerson.zip());
        Assertions.assertEquals(persona.phone(), createdPerson.phone());
        Assertions.assertEquals(persona.email(), createdPerson.email());
    }

    @Test
    void createPerson_throwPersonAlreadyExist_existingPerson() {
        // GIVEN a persona service
        // AND a person
        final var persona = new Person("John", "Doe", "1509 Baylee St", "NYC", "28015", "148-478-2156", "john.doe@mail.com");
        // WHEN we create the person
        // THEN an exception is thrown
        Assertions.assertThrows(PersonAlreadyExist.class, () -> personaService.createPerson(persona));
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

    @Test
    void getPersonByAddress_returnPersons_existingAddress() {
        // GIVEN a persona service
        // AND an address
        final var address = "1509 Baylee St";
        // WHEN we get the persons
        final var persons = personaService.getPersonByAddress(address);
        // THEN the persons are returned
        Assertions.assertNotNull(persons);
        Assertions.assertFalse(persons.isEmpty());
    }

    @Test
    void getPersonByAddress_returnEmptyList_nonExistingAddress() {
        // GIVEN a persona service
        // AND an address
        final var address = "1509 Baylee St";
        // WHEN we get the persons
        final var persons = personaService.getPersonByAddress(address);
        // THEN the persons are returned
        Assertions.assertNotNull(persons);
        Assertions.assertFalse(persons.isEmpty());
    }

    @Test
    void getPersonByFirstAndLastName_returnPersons_existingFirstAndLastName() {
        // GIVEN a persona service
        // AND a first and last name
        final var firstName = "John";
        final var lastName = "Doe";
        // WHEN we get the persons
        final var persons = personaService.getPersonByFirstAndLastName(firstName, lastName);
        // THEN the persons are returned
        Assertions.assertNotNull(persons);
        Assertions.assertFalse(persons.isEmpty());
    }

    @Test
    void getPersonByFirstAndLastName_returnEmptyList_nonExistingFirstAndLastName() {
        // GIVEN a persona service
        // AND a first and last name
        final var firstName = "John";
        final var lastName = "Doe";
        // WHEN we get the persons
        final var persons = personaService.getPersonByFirstAndLastName(firstName, lastName);
        // THEN the persons are returned
        Assertions.assertNotNull(persons);
        Assertions.assertFalse(persons.isEmpty());
    }

}
