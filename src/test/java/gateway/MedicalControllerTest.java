package gateway;

import fr.swynn.gateway.MedicalController;
import fr.swynn.model.MedicalRecord;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

@SpringBootApplication
@SpringBootTest(classes = MedicalController.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MedicalControllerTest {

    @Autowired
    private MedicalController controller;

    @BeforeEach
    void setUp() {
        controller = new MedicalController();
    }

    @Test
    void createMedicalRecord_returnMedicalRecord_existingMedicalRecord() {
        // GIVEN an inferno controller
        final var medicalRecordFirstName = "John";
        final var medicalRecordLastName = "Boyd";
        final var medicalRecordBirthdate = "03/06/1984";
        final var medicalRecordMedications = new String[] {"aznol:350mg", "hydrapermazol:100mg"};
        final var medicalRecordAllergies = new String[] {"nillacilan"};

        final var medicalRecord = new MedicalRecord(medicalRecordFirstName, medicalRecordLastName, medicalRecordBirthdate, medicalRecordMedications, medicalRecordAllergies);
        // WHEN we create a medicalRecord
        final var response = controller.createMedicalRecord(medicalRecord);
        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());

        // AND the medicalRecord is returned
        final var returnedMedicalRecord = response.getBody();
        Assertions.assertNotNull(returnedMedicalRecord);
        Assertions.assertEquals(medicalRecordFirstName, returnedMedicalRecord.firstName());
        Assertions.assertEquals(medicalRecordLastName, returnedMedicalRecord.lastName());
        Assertions.assertEquals(medicalRecordBirthdate, returnedMedicalRecord.birthdate());
        Assertions.assertEquals(medicalRecordMedications, returnedMedicalRecord.medications());
        Assertions.assertEquals(medicalRecordAllergies, returnedMedicalRecord.allergies());
    }

    @Test
    void createMedicalRecord_returnConflict_existingMedicalRecord() {
        // GIVEN an inferno controller
        final var medicalRecordFirstName = "John";
        final var medicalRecordLastName = "Doe";
        final var medicalRecordBirthdate = "03/06/1984";
        final var medicalRecordMedications = new String[] {"aznol:350mg", "hydrapermazol:100mg"};
        final var medicalRecordAllergies = new String[] {"nillacilan"};

        final var medicalRecord = new MedicalRecord(medicalRecordFirstName, medicalRecordLastName, medicalRecordBirthdate, medicalRecordMedications, medicalRecordAllergies);
        // WHEN we create a medicalRecord
        controller.createMedicalRecord(medicalRecord);
        final var response = controller.createMedicalRecord(medicalRecord);
        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void updateMedicalRecord_returnMedicalRecord_existingMedicalRecord() {
        // GIVEN an inferno controller
        final var medicalRecordFirstName = "John";
        final var medicalRecordLastName = "Doe";
        final var medicalRecordBirthdate = "03/06/1984";
        final var medicalRecordMedications = new String[] {"aznol:350mg", "hydrapermazol:100mg"};
        final var medicalRecordAllergies = new String[] {"nillacilan"};

        final var medicalRecord = new MedicalRecord(medicalRecordFirstName, medicalRecordLastName, medicalRecordBirthdate, medicalRecordMedications, medicalRecordAllergies);

        // WHEN we create a medicalRecord
        controller.createMedicalRecord(medicalRecord);
        final var response = controller.updateMedicalRecord(medicalRecord);
        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateMedicalRecord_returnNotFound_existingMedicalRecord() {
        // GIVEN an inferno controller
        final var medicalRecordFirstName = "John";
        final var medicalRecordLastName = "Boyd";
        final var medicalRecordBirthdate = "03/06/1984";
        final var medicalRecordMedications = new String[] {"aznol:350mg", "hydrapermazol:100mg"};
        final var medicalRecordAllergies = new String[] {"nillacilan"};

        final var medicalRecord = new MedicalRecord(medicalRecordFirstName, medicalRecordLastName, medicalRecordBirthdate, medicalRecordMedications, medicalRecordAllergies);

        // WHEN we create a medicalRecord
        final var response = controller.updateMedicalRecord(medicalRecord);
        // THEN we receive a not found response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void deleteMedicalRecord_returnMedicalRecord_existingMedicalRecord() {
        // GIVEN an inferno controller
        final var medicalRecordFirstName = "John";
        final var medicalRecordLastName = "Doe";
        final var medicalRecordBirthdate = "03/06/1984";
        final var medicalRecordMedications = new String[] {"aznol:350mg", "hydrapermazol:100mg"};
        final var medicalRecordAllergies = new String[] {"nillacilan"};

        final var medicalRecord = new MedicalRecord(medicalRecordFirstName, medicalRecordLastName, medicalRecordBirthdate, medicalRecordMedications, medicalRecordAllergies);

        // WHEN we delete a medicalRecord
        final var response = controller.deleteMedicalRecord(medicalRecord);
        // THEN we receive an ok response
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void deleteMedicalRecord_returnNotFound_existingMedicalRecord() {
        // GIVEN an inferno controller
        final var medicalRecordFirstName = "John";
        final var medicalRecordLastName = "Boyd";
        final var medicalRecordBirthdate = "03/06/1984";
        final var medicalRecordMedications = new String[] {"aznol:350mg", "hydrapermazol:100mg"};
        final var medicalRecordAllergies = new String[] {"nillacilan"};

        final var medicalRecord = new MedicalRecord(medicalRecordFirstName, medicalRecordLastName, medicalRecordBirthdate, medicalRecordMedications, medicalRecordAllergies);

        // WHEN we delete a medicalRecord
        final var response = controller.deleteMedicalRecord(medicalRecord);
        // THEN we receive a not found response
        Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
