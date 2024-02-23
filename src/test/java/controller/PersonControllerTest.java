package controller;

import fr.swynn.controller.PersonController;
import fr.swynn.model.Person;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@SpringBootTest(classes = PersonController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PersonControllerTest {

    @Autowired
    private PersonController controller;

    @BeforeEach
    void setUp() {
        controller = new PersonController();
    }

    @Test
    void deletePerson_successfullyDeleted_defaultState() {
        // GIVEN a person controller
        final var person = new Person("John", "Doe", "1234 Main St", "Springfield", "12345", "123-456-7890", "john.doe@mail.com");
        // WHEN a person is requested
        final var response = controller.deletePerson(person);

        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // AND the person is returned
        final var deletedPerson = response.getBody();

        Assertions.assertNotNull(deletedPerson);
    }

    @Test
    void deletedPerson_unknownPerson_exception() {
        // GIVEN a person controller
        final var person = new Person("John", "Boyd", "1234 Main St", "Springfield", "12345", "123-456-7890", "john.boyd@mail.com");
        // WHEN an unknown person is requested
        final var response = controller.deletePerson(person);
        // THEN the controller should return not found code.
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updatePerson_existingPerson_defaultState() {
        // GIVEN a person controller
        final var gatewayPerson = createPerson("John", "Doe");
        // WHEN the person is updated
        final var response = controller.updatePerson(gatewayPerson);

        // THE response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());

        // AND the person is returned
        final var updatedPerson = response.getBody();

        Assertions.assertNotNull(updatedPerson);
        Assertions.assertEquals(gatewayPerson.firstName(), updatedPerson.firstName());
        Assertions.assertEquals(gatewayPerson.lastName(), updatedPerson.lastName());
    }

    @Test
    void updatePerson_unknownPerson_exception() {
        // GIVEN a person controller
        final var gatewayPerson = createPerson("John", "Boyd");
        // WHEN an unknown person is updated
        final var response = controller.updatePerson(gatewayPerson);
        // THEN the controller should return not found code.
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void createPerson_newPerson_defaultState() {
        // GIVEN a person controller
        final var gatewayPerson = createPerson("Roger", "Ronald");
        // WHEN a new person is created
        final var response = controller.createPerson(gatewayPerson);

        // THEN the response is CREATED
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // THEN the person is returned
        final var createdPerson = response.getBody();

        Assertions.assertNotNull(createdPerson);
        Assertions.assertEquals(gatewayPerson.firstName(), createdPerson.firstName());
        Assertions.assertEquals(gatewayPerson.lastName(), createdPerson.lastName());
    }

    @Test
    void createPerson_existingPerson_exception() {
        // GIVEN a person controller
        final var gatewayPerson = createPerson("John", "Doe");
        // WHEN an existing person is created
        final var response = controller.createPerson(gatewayPerson);
        // THEN the controller should return not found code.
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void getCommunityEmail_listOfMail_existingCity() {
        // GIVEN a person controller
        // AND a city
        final var city = "Culver";
        // WHEN the list of mail is requested
        final var response = controller.getCommunityEmail(city);
        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // AND the list of mail is returned
        final var listOfMail = response.getBody();
        Assertions.assertNotNull(listOfMail);
        Assertions.assertFalse(listOfMail.isEmpty());
    }

    @Test
    void getCommunityEmail_emptyList_nonExistingCity() {
        // GIVEN a person controller
        // AND a city
        final var city = "Springfield";
        // WHEN the list of mail is requested
        final var response = controller.getCommunityEmail(city);
        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // AND the list of mail is returned
        final var listOfMail = response.getBody();
        Assertions.assertNotNull(listOfMail);
        Assertions.assertTrue(listOfMail.isEmpty());
    }

    @Test
    void getChildrenAlert_listOfChildren_existingAddress() {
        // GIVEN a person controller
        // AND an address
        final var address = "1509 Baylee St";
        // WHEN the list of children is requested
        final var response = controller.getChildrenAlert(address);
        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // AND the list of children is returned
        final var listOfChildren = response.getBody();
        Assertions.assertNotNull(listOfChildren);
        Assertions.assertFalse(listOfChildren.isEmpty());
    }

    @Test
    void getChildrenAlert_emptyList_nonExistingAddress() {
        // GIVEN a person controller
        // AND an address
        final var address = "1234 Main St";
        // WHEN the list of children is requested
        final var response = controller.getChildrenAlert(address);
        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // AND the list of children is returned
        final var listOfChildren = response.getBody();
        Assertions.assertNotNull(listOfChildren);
        Assertions.assertTrue(listOfChildren.isEmpty());
    }

    @Test
    void getPersonByFirstAndLastName_listOfPersons_existingFirstAndLastName() {
        // GIVEN a person controller
        // AND a first and last name
        final var firstName = "Gwen";
        final var lastName = "Boyd";
        // WHEN the list of persons is requested
        final var response = controller.getPersonByFirstAndLastName(firstName, lastName);
        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // AND the list of persons is returned
        final var listOfPersons = response.getBody();
        Assertions.assertNotNull(listOfPersons);
        Assertions.assertFalse(listOfPersons.isEmpty());
    }

    @Test
    void getPersonByFirstAndLastName_emptyList_nonExistingFirstAndLastName() {
        // GIVEN a person controller
        // AND a first and last name
        final var firstName = "John";
        final var lastName = "Boyd";
        // WHEN the list of persons is requested
        final var response = controller.getPersonByFirstAndLastName(firstName, lastName);
        // THEN the response is OK
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
        // AND the list of persons is returned
        final var listOfPersons = response.getBody();
        Assertions.assertNotNull(listOfPersons);
        Assertions.assertTrue(listOfPersons.isEmpty());
    }

    private Person createPerson(final String firstName, final String lastName) {
        final var address = "1234 Main St";
        final var city = "Springfield";
        final var zip = "12345";
        final var phone = "123-456-7890";
        final var email = firstName + "." + lastName + "@mail.com";
        return new Person(firstName, lastName, address, city, zip, phone, email);
    }
}
