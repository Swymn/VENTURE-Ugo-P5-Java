package gateway;

import service.impl.FakeFirestationService;
import service.impl.FakeMedicalService;
import service.impl.FakePersonService;
import fr.swynn.exception.*;
import fr.swynn.gateway.Gateway;
import fr.swynn.gateway.impl.GatewayProvider;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
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
        Assertions.assertEquals(4, expected.size());
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
        Assertions.assertEquals(4, expected.citizens().size());
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
    void getPhoneListByFirestation_returnListOfPhone_existingStation() {
        // GIVEN a gateway and a station
        final var station = "1";

        // WHEN getPhoneListByFirestation is called
        final var expected = gateway.getPhoneListByFirestation(station);

        // THEN the returned list contains 3 phones
        Assertions.assertEquals(4, expected.size());
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

    @Test
    void getHomeFire_returnHomeFire_existingAddress() throws UnknownFirestation {
        // GIVEN a gateway and an address
        final var address = "1509 Culver St";

        // WHEN getHomeFire is called
        final var expected = gateway.getHomeFire(address);

        // THEN the returned homeFire contains 3 persons
        Assertions.assertEquals(2, expected.peoples().size());
    }

    @Test
    void getHomeFire_returnHomeFire_nonExistingAddress() {
        // GIVEN a gateway and an address
        final var address = "unknown";

        // WHEN getHomeFire is called
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> gateway.getHomeFire(address));
    }
    @Test
    void getCitizenServedByStations_returnMapOfAddressCitizen_existingStations() throws UnknownFirestation {
        // GIVEN a gateway and a list of stations
        final var stations = new String[]{"1", "2"};

        // WHEN getCitizenServedByStations is called
        final var expected = gateway.getCitizenServedByStations(stations);

        // THEN the returned map contains 5 addresses
        Assertions.assertEquals(6, expected.size());
    }

    @Test
    void getCitizenServedByStations_returnMapOfAddressCitizen_nonExistingStations() {
        // GIVEN a gateway and a list of stations
        final var stations = new String[]{"-1", "-2"};

        // WHEN getCitizenServedByStations is called
        // THEN an exception is thrown
        Assertions.assertThrows(UnknownFirestation.class, () -> gateway.getCitizenServedByStations(stations));
    }

    @Test
    void getChildrensByAddress_returnListOfChildrens_existingAddress() {
        // GIVEN a gateway and an address
        final var address = "1509 Baylee St";

        // WHEN getChildrensByAddress is called
        final var expected = gateway.getChildrensByAddress(address);

        // THEN the returned list contains 2 childrens
        Assertions.assertEquals(1, expected.size());
    }

    @Test
    void getPersonByFirstNameAndLastName_returnListOfPerson_existingPerson() {
        // GIVEN a gateway and a person
        final var firstName = "John";
        final var lastName = "Doe";

        // WHEN getPersonByFirstNameAndLastName is called
        final var expected = gateway.getPersonByFirstAndLastName(firstName, lastName);

        // THEN the returned list contains 1 person
        Assertions.assertEquals(1, expected.size());
    }

    @Test
    void getPersonByFirstNameAndLastName_returnEmptyList_nonExistingPerson() {
        // GIVEN a gateway and a person
        final var firstName = "John";
        final var lastName = "Test";

        // WHEN getPersonByFirstNameAndLastName is called
        final var expected = gateway.getPersonByFirstAndLastName(firstName, lastName);

        // THEN the returned list contains 0 person
        Assertions.assertEquals(0, expected.size());
    }
}
