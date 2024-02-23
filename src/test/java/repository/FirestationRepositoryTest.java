package repository;

import fr.swynn.repository.FirestationRepository;
import fr.swynn.repository.impl.DefaultFirestationRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FirestationRepositoryTest {

    private FirestationRepository firestationRepository;

    @BeforeEach
    void setUp() {
        firestationRepository = new DefaultFirestationRepository();
    }

    @Test
    void getgetAllfirestations_shouldReturnAllFirestations_existingFirestations() {
        // GIVEN a firestation repository
        // WHEN getAllfirestations is called
        final var firestations = firestationRepository.getAllfirestations();

        // THEN the firestations list should not be empty
        // AND the firestations list should contain 3 firestations
        Assertions.assertFalse(firestations.isEmpty());
        Assertions.assertEquals(3, firestations.size());
    }
}
