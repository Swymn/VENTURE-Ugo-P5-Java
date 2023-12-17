package fr.swynn.gateway.web;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

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
    void getPerson_newPerson_defaultState() {
        // GIVEN a person controller
        final var firstName = "John";
        final var lastName = "Doe";
        // WHEN a person is requested
        final var person = controller.getPerson(firstName, lastName);
        // THEN the person is returned
        Assertions.assertEquals(firstName, person.firstName());
        Assertions.assertEquals(lastName, person.lastName());
    }
}
