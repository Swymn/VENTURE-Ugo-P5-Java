package fr.swynn.medical.data;

import fr.swynn.medical.impl.MedicalRecordAlreadyExist;
import fr.swynn.medical.impl.SNMedicalService;
import fr.swynn.medical.impl.UnknownMedicalRecord;
import fr.swynn.medical.model.MedicalRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MedicalServiceTest {

    private MedicalService medicalService;

    @BeforeEach
    void setUp() {
        medicalService = new SNMedicalService();
    }

    @Test
    void createMedicalRecord_returnMedicalRecord_nonExistingMedicalRecord() throws MedicalRecordAlreadyExist {
        // GIVEN a medical service
        // AND a medical record
        final var medicalRecord = new MedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);

        // WHEN creating the medical record
        final var createdMedicalRecord = medicalService.createMedicalRecord(medicalRecord);

        // THEN the medical record is returned
        Assertions.assertNotNull(createdMedicalRecord);
    }

    @Test
    void createMedicalRecord_throwMedicalRecordAlreadyExist_existingMedicalRecord() {
        // GIVEN a medical service
        // AND a medical record
        final var medicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);

        // WHEN creating the medical record
        // AND creating the medical record again
        Assertions.assertThrows(MedicalRecordAlreadyExist.class, () -> medicalService.createMedicalRecord(medicalRecord));
    }

    @Test
    void updateMedicalRecord_returnMedicalRecord_existingMedicalRecord() throws UnknownMedicalRecord {
        // GIVEN a medical service
        // AND a medical record
        final var medicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);

        // WHEN updating the medical record
        final var updatedMedicalRecord = medicalService.updateMedicalRecord(medicalRecord);

        // THEN the medical record is returned
        Assertions.assertNotNull(updatedMedicalRecord);
    }

    @Test
    void updateMedicalRecord_throwUnknownMedicalRecord_nonExistingMedicalRecord() {
        // GIVEN a medical service
        // AND a medical record
        final var medicalRecord = new MedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);

        // WHEN updating the medical record
        // AND updating the medical record again
        Assertions.assertThrows(UnknownMedicalRecord.class, () -> medicalService.updateMedicalRecord(medicalRecord));
    }

    @Test
    void deleteMedicalRecord_returnMedicalRecord_existingMedicalRecord() throws UnknownMedicalRecord {
        // GIVEN a medical service
        // AND a medical record
        final var medicalRecord = new MedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);

        // WHEN deleting the medical record
        final var deletedMedicalRecord = medicalService.deleteMedicalRecord(medicalRecord);

        // THEN the medical record is returned
        Assertions.assertNotNull(deletedMedicalRecord);
    }

    @Test
    void deleteMedicalRecord_throwUnknownMedicalRecord_nonExistingMedicalRecord() {
        // GIVEN a medical service
        // AND a medical record
        final var medicalRecord = new MedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);

        // WHEN deleting the medical record
        // AND deleting the medical record again
        Assertions.assertThrows(UnknownMedicalRecord.class, () -> medicalService.deleteMedicalRecord(medicalRecord));
    }
}
