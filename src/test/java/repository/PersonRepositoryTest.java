package repository;

import fr.swynn.repository.PersonRepository;
import fr.swynn.repository.impl.DefaultPersonRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PersonRepositoryTest {

    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository = new DefaultPersonRepository();
    }

    @Test
    void getAllPersons_shouldReturnAllPersons_existingPersons() {
        // GIVEN a person repository
        // WHEN getAllPersons is called
        final var persons = personRepository.getAllPerson();

        // THEN the persons list should not be empty
        // AND the persons list should contain 3 persons
        Assertions.assertFalse(persons.isEmpty());
        Assertions.assertEquals(3, persons.size());
    }
}
