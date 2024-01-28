package gateway;

import core.FakeFirestationService;
import core.FakeGateway;
import core.FakeMedicalService;
import core.FakePersonService;
import fr.swynn.core.Gateway;
import fr.swynn.gateway.GatewayProvider;
import fr.swynn.gateway.SafetyNetGateway;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import fr.swynn.service.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GatewayTest {

    private final Gateway gateway = new GatewayProvider().getGateway();

    @BeforeEach
    void setUp() {
        FakeFirestationService.initFirestations();
        FakePersonService.initPerson();
        FakeMedicalService.initMedicalRecord();
    }

    @Test
    void getCommunityEmail_returnListOfEmail_existingCity() {
        // GIVEN a gateway and a city
        final var city = "Culver";

        // WHEN getCommunityEmail is called
        final var expected = gateway.getCommunityEmail(city);

        // THEN the returned list contains 23 emails
        Assertions.assertEquals(3, expected.size());
    }

    @Test
    void getCommunityEmail_returnEmptyList_nonExistingCity() {
        // GIVEN a gateway and a city
        final var city = "Test";

        // WHEN getCommunityEmail is called
        final var expected = gateway.getCommunityEmail(city);

        // THEN the returned list contains 23 emails
        Assertions.assertEquals(0, expected.size());
    }

    @Test
    void deletePerson_shouldNotThrowError_existingPerson() {
        // GIVEN a gateway and a person
        final var person = new Person("John", "Doe", "1 rue de la Paix", "Paris", "75000", "0123456789", "john.doe@mail.com");

        // WHEN deletePerson is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.deletePerson(person));
    }

    @Test
    void deletePerson_shouldThrowError_nonExistingPerson() {
        // GIVEN a gateway and a person
        final var person = new Person("John", "Test", "1 rue de la Paix", "Paris", "75000", "0123456789", "john.test@mail.com");

        // WHEN deletePerson is called
        // THEN no error is thrown
        Assertions.assertThrows(UnknownPerson.class , () -> gateway.deletePerson(person));
    }

    @Test
    void updatePerson_shouldNotThrowError_existingPerson() {
        // GIVEN a gateway and a person
        final var person = new Person("John", "Doe", "1 rue de la Paix", "Paris", "75000", "0123456789", "john.doe@mail.com");

        // WHEN updatePerson is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.updatePerson(person));
    }

    @Test
    void updatePerson_shouldThrowError_nonExistingPerson() {
        // GIVEN a gateway and a person
        final var person = new Person("John", "Test", "1 rue de la Paix", "Paris", "75000", "0123456789", "john.test@mail.com");

        // WHEN updatePerson is called
        // THEN no error is thrown
        Assertions.assertThrows(UnknownPerson.class , () -> gateway.updatePerson(person));
    }

    @Test
    void createPerson_shouldNotThrowError_existingPerson() {
        // GIVEN a gateway and a person
        final var person = new Person("John", "Test", "1 rue de la Paix", "Paris", "75000", "0123456789", "john.test@mail.com");

        // WHEN createPerson is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.createPerson(person));
    }

    @Test
    void createPerson_shouldThrowError_nonExistingPerson() {
        // GIVEN a gateway and a person
        final var person = new Person("John", "Doe", "1 rue de la Paix", "Paris", "75000", "0123456789", "john.doe@mail.com");

        // WHEN createPerson is called
        // THEN no error is thrown
        Assertions.assertThrows(PersonAlreadyExist.class , () -> gateway.createPerson(person));
    }

    @Test
    void getPersonByStationNumber_returnListOfPerson_existingStation() throws UnknownFirestation {
        // GIVEN a gateway and a station
        final var station = "1";

        // WHEN getPersonByStationNumber is called
        final var expected = gateway.getPersonByStationNumber(station);

        // THEN the returned list contains 3 persons
        Assertions.assertEquals(3, expected.citizens().size());
    }

    @Test
    void getPersonByStationNumber_throwException_nonExistingStation() {
        // GIVEN a gateway and a station
        final var station = "-10";

        // WHEN getPersonByStationNumber is called
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> gateway.getPersonByStationNumber(station));
    }

    @Test
    void deleteFirestation_shouldNotThrowError_existingFirestation() {
        // GIVEN a gateway and a firestation
        final var firestation = new Firestation("1509 Baylee St", "1");

        // WHEN deleteFirestation is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.deleteFirestation(firestation));
    }

    @Test
    void deleteFirestation_shouldThrowError_nonExistingFirestation() {
        // GIVEN a gateway and a firestation
        final var firestation = new Firestation("unknown", "1");

        // WHEN deleteFirestation is called
        // THEN no error is thrown
        Assertions.assertThrows(UnknownFirestation.class , () -> gateway.deleteFirestation(firestation));
    }

    @Test
    void updateFirestation_shouldNotThrowError_existingFirestation() {
        // GIVEN a gateway and a firestation
        final var firestation = new Firestation("1509 Baylee St", "1");

        // WHEN updateFirestation is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.updateFirestation(firestation));
    }

    @Test
    void updateFirestation_shouldThrowError_nonExistingFirestation() {
        // GIVEN a gateway and a firestation
        final var firestation = new Firestation("unknown", "1");

        // WHEN updateFirestation is called
        // THEN no error is thrown
        Assertions.assertThrows(UnknownFirestation.class , () -> gateway.updateFirestation(firestation));
    }

    @Test
    void createFirestation_shouldNotThrowError_existingFirestation() {
        // GIVEN a gateway and a firestation
        final var firestation = new Firestation("unknown", "1");

        // WHEN createFirestation is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.createFirestation(firestation));
    }

    @Test
    void createFirestation_shouldThrowError_nonExistingFirestation() {
        // GIVEN a gateway and a firestation
        final var firestation = new Firestation("1509 Baylee St", "1");

        // WHEN createFirestation is called
        // THEN no error is thrown
        Assertions.assertThrows(FirestationAlreadyExist.class , () -> gateway.createFirestation(firestation));
    }

    @Test
    void deleteMedicalRecord_shouldNotThrowError_existingMedicalRecord() {
        // GIVEN a gateway and a firestation
        final var firestation = new MedicalRecord("John", "Doe", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"});

        // WHEN deleteMedicalRecord is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.deleteMedicalRecord(firestation));
    }

    @Test
    void deleteMedicalRecord_shouldThrowError_nonExistingMedicalRecord() {
        // GIVEN a gateway and a firestation
        final var firestation = new MedicalRecord("John", "Test", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"});

        // WHEN deleteMedicalRecord is called
        // THEN no error is thrown
        Assertions.assertThrows(UnknownMedicalRecord.class , () -> gateway.deleteMedicalRecord(firestation));
    }

    @Test
    void updateMedicalRecord_shouldNotThrowError_existingMedicalRecord() {
        // GIVEN a gateway and a firestation
        final var firestation = new MedicalRecord("John", "Doe", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"});

        // WHEN updateMedicalRecord is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.updateMedicalRecord(firestation));
    }

    @Test
    void updateMedicalRecord_shouldThrowError_nonExistingMedicalRecord() {
        // GIVEN a gateway and a firestation
        final var firestation = new MedicalRecord("John", "Test", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"});

        // WHEN updateMedicalRecord is called
        // THEN no error is thrown
        Assertions.assertThrows(UnknownMedicalRecord.class , () -> gateway.updateMedicalRecord(firestation));
    }

    @Test
    void createMedicalRecord_shouldNotThrowError_existingMedicalRecord() {
        // GIVEN a gateway and a firestation
        final var firestation = new MedicalRecord("John", "Test", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"});

        // WHEN createMedicalRecord is called
        // THEN no error is thrown
        Assertions.assertDoesNotThrow(() -> gateway.createMedicalRecord(firestation));
    }

    @Test
    void createMedicalRecord_shouldThrowError_nonExistingMedicalRecord() {
        // GIVEN a gateway and a firestation
        final var firestation = new MedicalRecord("John", "Doe", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"});

        // WHEN createMedicalRecord is called
        // THEN no error is thrown
        Assertions.assertThrows(MedicalRecordAlreadyExist.class , () -> gateway.createMedicalRecord(firestation));
    }
}