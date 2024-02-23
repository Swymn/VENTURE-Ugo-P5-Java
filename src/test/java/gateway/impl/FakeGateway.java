package gateway.impl;

import fr.swynn.dto.*;
import fr.swynn.exception.*;
import fr.swynn.gateway.Gateway;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


public class FakeGateway implements Gateway {

    private static final Logger LOGGER = LoggerFactory.getLogger(FakeGateway.class);
    public static final String NO_MEDICAL_RECORD_FOUND_FOR_AT = "No medical record found for {} {} at {}";

    private final List<Person> persons;
    private final List<Firestation> firestations;
    private final List<MedicalRecord> medicalRecords;

    public FakeGateway() {
        persons = getPersons();
        firestations = getFirestations();
        medicalRecords = getMedicalRecords();
    }

    private List<Person> getPersons() {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "1509 Baylee St",
                "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        persons.add(new Person("Jacob", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        persons.add(new Person("Roger", "Boyd", "1509 Culver St",
                "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));
        persons.add(new Person("Gwen", "Boyd", "1509 Baylee St",
                "Culver", "97451", "841-874-6512", "gwen.boyd@mail.com"));

        return persons;
    }

    private List<Firestation> getFirestations() {
        final List<Firestation> firestations = new ArrayList<>();
        firestations.add(new Firestation("1509 Culver St", "3"));
        firestations.add(new Firestation("29 15th St", "2"));
        firestations.add(new Firestation("834 Binoc Ave", "3"));
        firestations.add(new Firestation("644 Gershwin Cir", "1"));
        firestations.add(new Firestation("748 Townings Dr", "3"));
        firestations.add(new Firestation("112 Steppes Pl", "3"));

        return firestations;
    }

    private List<MedicalRecord> getMedicalRecords() {
        final var medicalRecords = new ArrayList<MedicalRecord>();

        medicalRecords.add(new MedicalRecord("John", "Doe", "03/06/1984",
                new String[]{"aznol:350mg", "hydrapermazol:100mg"},
                new String[]{"nillacilan"}));
        medicalRecords.add(new MedicalRecord("Jacob", "Boyd", "03/06/1989",
                new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol" +
                        ":250mg"}, new String[]{}));
        medicalRecords.add(new MedicalRecord("Tenley", "Boyd", "03/06/1989",
                new String[]{"noxidian:100mg", "pharmacol:7500mg",
                        "hydrapermazol:100mg", "pharmacol:1000mg"},
                new String[]{"nillacilan"}));
        medicalRecords.add(new MedicalRecord("Roger", "Boyd", "03/06/1989",
                new String[]{"naproxen:1000mg", "pharmacol:2500mg", "terazine" +
                        ":500mg", "noznazol:250mg"}, new String[]{"nillacilan"
        }));
        medicalRecords.add(new MedicalRecord("Gwen", "Boyd", "03/06/2015",
                new String[]{"aznol:350mg", "hydrapermazol:100mg"},
                new String[]{"nillacilan"}));

        return medicalRecords;
    }

    @Override
    public List<String> getCommunityEmail(final String city) {
        final List<String> emails = new ArrayList<>();
        for (final var persona : persons) {
            if (persona.city().equals(city)) {
                emails.add(persona.email());
            }
        }

        return emails;
    }

    @Override
    public List<DetailedCitizen> getPersonByFirstAndLastName(final String firstName, final String lastName) {
        return persons.stream()
                .filter(person -> person.firstName().equals(firstName) && person.lastName().equals(lastName))
                .map(person -> {
                    try {
                        final var medicalRecord = getMedicalRecord(person.firstName(), person.lastName());
                        return new DetailedCitizen(person.firstName(), person.lastName(), person.address(), person.email(), medicalRecord.medications(), medicalRecord.allergies());
                    } catch (UnknownMedicalRecord unknownMedicalRecord) {
                        LOGGER.warn(NO_MEDICAL_RECORD_FOUND_FOR_AT, person.firstName(), person.lastName(), person.address());
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Person deletePerson(final Person person) throws UnknownPerson {
        for (int i = 0; i < persons.size(); i++) {
            final var personaInList = persons.get(i);
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                persons.remove(i);
                return person;
            }
        }

        throw new UnknownPerson(person.firstName(), person.lastName());
    }

    @Override
    public Person updatePerson(final Person person) throws UnknownPerson {
        for (int i = 0; i < persons.size(); i++) {
            final var personaInList = persons.get(i);
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                persons.set(i, person);
                return person;
            }
        }
        throw new UnknownPerson(person.firstName(), person.lastName());
    }

    @Override
    public Person createPerson(final Person person) throws PersonAlreadyExist {
        for (final var personaInList : persons) {
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                throw new PersonAlreadyExist(person.firstName(),
                        person.lastName());
            }
        }

        persons.add(person);
        return person;
    }

    @Override
    public CitizenPayload getPersonByStationNumber(final String station) throws UnknownFirestation {
        final var personas = getPeopleByStation(station);

        final var citizens = new ArrayList<Citizen>();
        var adultCount = 0;
        var childCount = 0;

        for (final var persona : personas) {
            final var citizen = parsePersonToCitizen(persona);

            if (isAdult(persona)) {
                adultCount++;
            } else {
                childCount++;
            }

            citizens.add(citizen);
        }

        return new CitizenPayload(citizens, adultCount, childCount);
    }

    private List<String> getCoveredAddressByStation(final String station) throws UnknownFirestation {
        final var stationsAddress =
                firestations.stream().filter(firestation -> firestation.station().equals(station))
                        .map(Firestation::address)
                        .toList();

        if (stationsAddress.isEmpty()) throw new UnknownFirestation(station);
        return stationsAddress;
    }

    private ArrayList<Person> getPeopleByStation(String station) throws UnknownFirestation {
        final var stationsAddress = getCoveredAddressByStation(station);
        final var personas = new ArrayList<Person>();
        for (final var address : stationsAddress) {
            for (final var persona : persons) {
                if (persona.address().equals(address)) {
                    personas.add(persona);
                }
            }
        }
        return personas;
    }

    private Citizen parsePersonToCitizen(final Person person) {
        return new Citizen(person.firstName(), person.lastName(),
                person.address(), person.phone());
    }

    private int getAge(final Person person) {
        try {
            final var medicalRecord = getMedicalRecord(person.firstName(),
                    person.lastName());
            final var dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            final var birthDate = dateFormat.parse(medicalRecord.birthdate());

            final var currentDate = Calendar.getInstance();
            final var now = currentDate.getTime();

            long ageInMillis = now.getTime() - birthDate.getTime();
            long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);

            return (int) ageInYears;
        } catch (final UnknownMedicalRecord |
                       ParseException unknownMedicalRecord) {
            return 0;
        }
    }

    private boolean isAdult(final Person person) {
        return getAge(person) > 18;
    }

    private MedicalRecord getMedicalRecord(final String firstName,
                                           final String lastName) throws UnknownMedicalRecord {
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.firstName().equals(firstName) && medicalRecord.lastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new UnknownMedicalRecord(firstName,
                        lastName));
    }

    @Override
    public Map<String, CitizenMedicalHistory[]> getCitizenServedByStations(String[] stations) throws UnknownFirestation {
        final var citizenMedicalHistories = new HashMap<String, CitizenMedicalHistory[]>();
        final var addressCoveredByStation = getAddressCoveredByStations(stations);

        for (final var address : addressCoveredByStation) {
            final var personsInsideHouse = getPersonsInsideHouse(address);

            citizenMedicalHistories.put(address, personsInsideHouse);
        }

        return citizenMedicalHistories;
    }
    private List<String> getAddressCoveredByStations(final String[] stations) throws UnknownFirestation {
        final List<String> addressCoveredByStation = new ArrayList<>();
        for (final var station : stations) {
            final var addressCoveredByStationForStation = getCoveredAddressByStation(station);
            addressCoveredByStation.addAll(addressCoveredByStationForStation);
        }
        return addressCoveredByStation;
    }

    private CitizenMedicalHistory[] getPersonsInsideHouse(final String address) {
        return persons.stream()
                .filter(person -> person.address().equals(address))
                .map(person -> {
                    try {
                        final var medicalHistory = getMedicalHistory(person.firstName(), person.lastName());
                        final var age = getAge(person);
                        return new CitizenMedicalHistory(person.firstName(), person.lastName(), person.phone(), age, medicalHistory.medications(), medicalHistory.allergies());
                    } catch (UnknownMedicalRecord unknownMedicalRecord) {
                        LOGGER.warn(NO_MEDICAL_RECORD_FOUND_FOR_AT, person.firstName(), person.lastName(), address);
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toArray(CitizenMedicalHistory[]::new);
    }

    @Override
    public Firestation createFirestation(final Firestation firestation) throws FirestationAlreadyExist {
        for (final var firestationInList : firestations) {
            if (firestationInList.address().equals(firestation.address())) {
                throw new FirestationAlreadyExist(firestation.address());
            }
        }

        firestations.add(firestation);
        return firestation;
    }

    @Override
    public Firestation updateFirestation(Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address())) {
                firestations.set(i, firestation);
                return firestation;
            }
        }

        throw new UnknownFirestation(firestation.address());
    }

    @Override
    public Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address())) {
                firestations.remove(i);
                return firestation;
            }
        }

        throw new UnknownFirestation(firestation.address());
    }

    @Override
    public MedicalRecord createMedicalRecord(final MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist {
        for (final var medicalRecordInList : medicalRecords) {
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                throw new MedicalRecordAlreadyExist(medicalRecord.firstName()
                        , medicalRecord.lastName());
            }
        }

        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public MedicalRecord updateMedicalRecord(final MedicalRecord medicalRecord) throws UnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.set(i, medicalRecord);
                return medicalRecord;
            }
        }

        throw new UnknownMedicalRecord(medicalRecord.firstName(),
                medicalRecord.lastName());
    }

    @Override
    public MedicalRecord deleteMedicalRecord(final MedicalRecord medicalRecord) throws UnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.remove(i);
                return medicalRecord;
            }
        }

        throw new UnknownMedicalRecord(medicalRecord.firstName(),
                medicalRecord.lastName());
    }

    @Override
    public List<String> getPhoneListByFirestation(final String station) {
        try {
            final List<String> coveredAddress =
                    getCoveredAddressByStation(station);
            return persons.stream()
                    .filter(person -> coveredAddress.contains(person.address()))
                    .map(Person::phone)
                    .toList();
        } catch (UnknownFirestation unknownFirestation) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<ChildCitizen> getChildrensByAddress(final String address) {
        final var personsInsideHouse = persons.stream()
                .filter(person -> person.address().equals(address)).toList();
        final var childrenInsideHouse = personsInsideHouse.stream()
                .filter(person -> !isAdult(person)).toList();
        final var children = childrenInsideHouse.stream()
                .map(person -> new ChildCitizen(person.firstName(), person.lastName(), getFamilyMembers(person)));

        return children.toList();
    }

    private List<Citizen> getFamilyMembers(final Person person) {
        return persons.stream()
                .filter(p -> p.address().equals(person.address()))
                .filter(p -> !p.firstName().equals(person.firstName()) && !p.lastName().equals(person.lastName()))
                .map(p -> new Citizen(p.firstName(), p.lastName(), p.address(), p.phone()))
                .toList();
    }

    @Override
    public HomeFire getHomeFire(String address) throws UnknownFirestation {
        final var firestation = firestations.stream()
                .filter(firestation1 -> firestation1.address().equals(address))
                .findFirst()
                .orElseThrow(() -> new UnknownFirestation(address));

        final var personsInsideHouse = persons.stream()
                .filter(person -> person.address().equals(address))
                .map(person -> {
                    try {
                        final var medicalHistory = getMedicalHistory(person.firstName(), person.lastName());
                        return new HomePeople(person.firstName(), person.lastName(), person.phone(), medicalHistory.medications(), medicalHistory.allergies());
                    } catch (UnknownMedicalRecord e) {
                        LOGGER.warn(NO_MEDICAL_RECORD_FOUND_FOR_AT, person.firstName(), person.lastName(), address);
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toList();

        return new HomeFire(personsInsideHouse, firestation.station());
    }

    private MedicalHistory getMedicalHistory(final String firstName, final String lastName) throws UnknownMedicalRecord{
        final var medicalRecord = medicalRecords.stream()
                .filter(record -> record.firstName().equals(firstName) && record.lastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new UnknownMedicalRecord(firstName, lastName));

        return new MedicalHistory(medicalRecord.medications(), medicalRecord.allergies());
    }
}

