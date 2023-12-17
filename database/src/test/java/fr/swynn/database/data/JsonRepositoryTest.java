package fr.swynn.database.data;

import fr.swynn.database.impl.JsonRepositoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonRepositoryTest {

    private JsonRepository repository;

    @BeforeEach
    void setUp() {
        final var fileName = "testdata.json";
        repository = new JsonRepositoryImpl(fileName);
    }

    @Test
    void getJsonData_nonEmptyNode_existingData() {
        // GIVEN a json repository
        final var dataCategory = "persons";
        // WHEN we requested the repository
        final var data = repository.getJsonData(dataCategory);
        // THEN we will get some values
        Assertions.assertNotNull(data);
        Assertions.assertTrue(data.isArray());
    }

    @Test
    void getJsonData_nullNode_nonExistingKey() {
        // GIVEN a json repository
        final var dataCategory = "nonExistingKey";
        // WHEN we requested the repository
        final var data = repository.getJsonData(dataCategory);
        // THEN we will get some values
        Assertions.assertNull(data);
    }
}
