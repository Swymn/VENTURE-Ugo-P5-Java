package fr.swynn.launcher;

import fr.swynn.gateway.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GatewayToMedicalServiceTest {

    private Gateway center;

    @BeforeEach
    void setUp() {
        center = ConfiguredGatewayProxy.gateway();
    }

    @Test
    void createMedicalRecord_returnMedicalRecord_nonExistingMedicalRecord() throws GatewayMedicalRecordAlreadyExist {
        // GIVEN a medical service
        // AND a medical record that does not exist
        final var medicalRecord = new GatewayMedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);
        // WHEN we create the medical record
        final var createdMedicalRecord = center.createMedicalRecord(medicalRecord);
        // THEN the created medical record shouldn't be null
        Assertions.assertNotNull(createdMedicalRecord);
    }

    @Test
    void createMedicalRecord_throwException_existingMedicalRecord() {
        // GIVEN a medical service
        // AND a medical record that exists
        final var medicalRecord = new GatewayMedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);
        // WHEN we create the medical record
        // THEN the created medical record should throw an exception
        Assertions.assertThrows(GatewayMedicalRecordAlreadyExist.class, () -> center.createMedicalRecord(medicalRecord));
    }

    @Test
    void updateMedicalRecord_returnMedicalRecord_existingMedicalRecord() throws GatewayUnknownMedicalRecord {
        // GIVEN a medical service
        // AND a medical record that exists
        final var medicalRecord = new GatewayMedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);
        // WHEN we update the medical record
        final var updatedMedicalRecord = center.updateMedicalRecord(medicalRecord);
        // THEN the updated medical record shouldn't be null
        Assertions.assertNotNull(updatedMedicalRecord);
    }

    @Test
    void updateMedicalRecord_throwException_nonExistingMedicalRecord() {
        // GIVEN a medical service
        // AND a medical record that does not exist
        final var medicalRecord = new GatewayMedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);
        // WHEN we update the medical record
        // THEN the updated medical record should throw an exception
        Assertions.assertThrows(GatewayUnknownMedicalRecord.class, () -> center.updateMedicalRecord(medicalRecord));
    }

    @Test
    void deleteMedicalRecord_returnMedicalRecord_existingMedicalRecord() throws GatewayUnknownMedicalRecord {
        // GIVEN a medical service
        // AND a medical record that exists
        final var medicalRecord = new GatewayMedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);
        // WHEN we delete the medical record
        final var deletedMedicalRecord = center.deleteMedicalRecord(medicalRecord);
        // THEN the deleted medical record shouldn't be null
        Assertions.assertNotNull(deletedMedicalRecord);
    }

    @Test
    void deleteMedicalRecord_throwException_nonExistingMedicalRecord() {
        // GIVEN a medical service
        // AND a medical record that does not exist
        final var medicalRecord = new GatewayMedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);
        // WHEN we delete the medical record
        // THEN the deleted medical record should throw an exception
        Assertions.assertThrows(GatewayUnknownMedicalRecord.class, () -> center.deleteMedicalRecord(medicalRecord));
    }
}