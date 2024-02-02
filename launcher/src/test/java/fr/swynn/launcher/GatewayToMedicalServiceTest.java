package fr.swynn.launcher;

import fr.swynn.gateway.core.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GatewayToMedicalServiceTest {

    private GatewayToMedicalService service;

    @BeforeEach
    void setUp() {
        service = new GatewayToMedicalService();
    }

    @Test
    void createMedicalRecord_returnMedicalRecord_nonExistingMedicalRecord() throws GatewayMedicalRecordAlreadyExist {
        // GIVEN a medical service
        // AND a medical record that does not exist
        final var medicalRecord = new GatewayMedicalRecord("John", "Boyd", "01/01/2000", new String[0], new String[0]);
        // WHEN we create the medical record
        final var createdMedicalRecord = service.createMedicalRecord(medicalRecord);
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
        Assertions.assertThrows(GatewayMedicalRecordAlreadyExist.class, () -> service.createMedicalRecord(medicalRecord));
    }

    @Test
    void updateMedicalRecord_returnMedicalRecord_existingMedicalRecord() throws GatewayUnknownMedicalRecord {
        // GIVEN a medical service
        // AND a medical record that exists
        final var medicalRecord = new GatewayMedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);
        // WHEN we update the medical record
        final var updatedMedicalRecord = service.updateMedicalRecord(medicalRecord);
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
        Assertions.assertThrows(GatewayUnknownMedicalRecord.class, () -> service.updateMedicalRecord(medicalRecord));
    }

    @Test
    void deleteMedicalRecord_returnMedicalRecord_existingMedicalRecord() throws GatewayUnknownMedicalRecord {
        // GIVEN a medical service
        // AND a medical record that exists
        final var medicalRecord = new GatewayMedicalRecord("John", "Doe", "01/01/2000", new String[0], new String[0]);
        // WHEN we delete the medical record
        final var deletedMedicalRecord = service.deleteMedicalRecord(medicalRecord);
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
        Assertions.assertThrows(GatewayUnknownMedicalRecord.class, () -> service.deleteMedicalRecord(medicalRecord));
    }

    @Test
    void getMedicalRecords_listOfMedicalRecords_exisingMedicalRecords() {
        // GIVEN a medical service
        // AND a first and last name
        final var firstName = "John";
        final var lastName = "Doe";
        // WHEN we get the medical records
        final var medicalRecords = service.getMedicalRecords(firstName, lastName);
        // THEN the medical records should not be empty
        Assertions.assertFalse(medicalRecords.isEmpty());
    }
}
