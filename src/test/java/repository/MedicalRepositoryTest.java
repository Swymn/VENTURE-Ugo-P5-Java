package repository;

import fr.swynn.repository.MedicalRepository;
import fr.swynn.repository.impl.DefaultMedicalRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicalRepositoryTest {

    private MedicalRepository medicalRepository;

    @BeforeEach
    void setUp() {
        medicalRepository = new DefaultMedicalRepository();
    }

    @Test
    void getAllMedicalRecords_shouldReturnAllMedicalRecords_existingMedicalRecords() {
        // GIVEN a medical repository
        // WHEN getAllMedicalRecords is called
        final var medicalRecords = medicalRepository.getAllMedicalRecords();

        // THEN the medicalRecords list should not be empty
        // AND the medicalRecords list should contain 3 medicalRecords
        Assertions.assertFalse(medicalRecords.isEmpty());
        Assertions.assertEquals(3, medicalRecords.size());
    }
}
