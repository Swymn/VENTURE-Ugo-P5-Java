package fr.swynn.gateway.impl;

import fr.swynn.dto.*;
import fr.swynn.exception.*;
import fr.swynn.gateway.Gateway;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import fr.swynn.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SafetyNetGateway implements Gateway {

    private static final Logger LOGGER;
    private static final String GATEWAY_LOADED_WITH_PROXY;
    private static final String GATEWAY_LOADED;
    private static final String DATE_FORMAT;
    public static final String UNABLE_TO_FIND_MEDICAL_RECORD_FOR = "Unable to find medical record for {} {}";

    private static Gateway instance;

    private PersonService personService;
    private FirestationService firestationService;
    private MedicalService medicalService;

    static {
        LOGGER = LoggerFactory.getLogger(SafetyNetGateway.class);
        GATEWAY_LOADED_WITH_PROXY = "Connection to Gateway loaded with {} proxy.";
        GATEWAY_LOADED = "Gateway instanced and loaded";
        DATE_FORMAT = "dd/MM/yyyy";
    }

    public static Gateway getInstance() {
        if (instance == null) {
            instance = new SafetyNetGateway();
        }
        return instance;
    }

    private SafetyNetGateway() {
        loadPersonService();
        loadFirestationService();
        loadMedicalService();
        LOGGER.info(GATEWAY_LOADED);
    }

    private void loadPersonService() {
        final var proxy = ServiceLoader.load(PersonService.class);
        personService = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, "PersonService");
    }

    private void loadFirestationService() {
        final var proxy = ServiceLoader.load(FirestationService.class);
        firestationService = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, "FirestationService");
    }

    private void loadMedicalService() {
        final var proxy = ServiceLoader.load(MedicalService.class);
        medicalService = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, "MedicalService");
    }

    @Override
    public List<String> getCommunityEmail(final String city) {
        return personService.getCommunityEmail(city);
    }

    @Override
    public List<DetailedCitizen> getPersonByFirstAndLastName(final String firstName, final String lastName) {
        final var persons = personService.getPersonByFirstAndLastName(firstName, lastName);
        return persons.stream()
                .map(this::parsePersonToDetailedCitizen)
                .filter(Objects::nonNull)
                .toList();
    }

    private DetailedCitizen parsePersonToDetailedCitizen(final Person person) {
        try {
            final var medicalRecord = medicalService.getMedicalRecord(person.firstName(), person.lastName());
            return new DetailedCitizen(person.firstName(), person.lastName(), person.address(), person.email(), medicalRecord.medications(), medicalRecord.allergies());
        } catch (UnknownMedicalRecord unknownMedicalRecord) {
            LOGGER.warn(UNABLE_TO_FIND_MEDICAL_RECORD_FOR, person.firstName(), person.lastName());
            return null;
        }
    }

    @Override
    public Person deletePerson(final Person person) throws UnknownPerson {
        return personService.deletePerson(person);
    }

    @Override
    public Person updatePerson(final Person person) throws UnknownPerson {
        return personService.updatePerson(person);
    }

    @Override
    public Person createPerson(final Person person) throws PersonAlreadyExist {
        return personService.createPerson(person);
    }

    @Override
    public CitizenPayload getPersonByStationNumber(final String station) throws UnknownFirestation {
        final var stationsAddress = firestationService.getFirestationAddressByStationNumber(station);
        final List<Person> persons = new ArrayList<>();

        for (final var address : stationsAddress) {
            final var currentPersonas = personService.getPersonByAddress(address);
            persons.addAll(currentPersonas);
        }

        return generateCitizenPayloadFromPersons(persons);
    }

    private CitizenPayload generateCitizenPayloadFromPersons(final List<Person> persons) {
        final List<Citizen> citizens = new ArrayList<>();
        var adultCount = 0;
        var childCount = 0;

        for (final var person : persons) {
            final var citizen = parsePersonToCitizen(person);

            if (isAdult(person)) {
                adultCount++;
            } else {
                childCount++;
            }

            citizens.add(citizen);
        }

        return new CitizenPayload(citizens, adultCount, childCount);
    }

    private Citizen parsePersonToCitizen(final Person person) {
        return new Citizen(person.firstName(), person.lastName(), person.address(), person.phone(), getAge(person));
    }

    private boolean isAdult(final Person person) {
        return getAge(person) > 18;
    }

    private int getAge(Person person) {
        try {
            final var medicalRecord = medicalService.getMedicalRecord(person.firstName(), person.lastName());
            final var dateFormat = new SimpleDateFormat(DATE_FORMAT);
            final var birthDate = dateFormat.parse(medicalRecord.birthdate());

            final var currentDate = Calendar.getInstance();
            final var now = currentDate.getTime();

            long ageInMillis = now.getTime() - birthDate.getTime();
            long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);

            return (int) ageInYears;
        } catch (final UnknownMedicalRecord | ParseException unknownMedicalRecord) {
            LOGGER.warn(UNABLE_TO_FIND_MEDICAL_RECORD_FOR, person.firstName(), person.lastName());
            return 0;
        }
    }

    @Override
    public List<ChildCitizen> getChildrensByAddress(final String address) {
        final var persons = personService.getPersonByAddress(address);

        return persons.stream()
                .filter(person -> !isAdult(person))
                .map(child -> parsePersonToChildCitizen(child, persons))
                .toList();
    }

    private ChildCitizen parsePersonToChildCitizen(final Person person, final List<Person> familyMembers) {
        final var familyMembersWithoutChild = familyMembers.stream()
                .filter(member -> !member.equals(person))
                .map(member -> new Citizen(member.firstName(), member.lastName(), member.address(), member.phone(), getAge(member)))
                .toList();

        return new ChildCitizen(person.firstName(), person.lastName(), getAge(person), familyMembersWithoutChild);
    }

    @Override
    public List<String> getPhoneListByFirestation(final String station) {
        try {
            final var stationsAddress = firestationService.getFirestationAddressByStationNumber(station);
            final List<String> phoneList = new ArrayList<>();

            for (final var address : stationsAddress) {
                final var currentPersonas = personService.getPersonByAddress(address);
                for (final var person : currentPersonas) {
                    phoneList.add(person.phone());
                }
            }

            return phoneList;
        } catch (final UnknownFirestation unknownFirestation) {
            return List.of();
        }
    }

    @Override
    public HomeFire getHomeFire(final String address) throws UnknownFirestation {
        final var firestationNumber = firestationService.getFirestationNumberByAddress(address);
        final var persons = personService.getPersonByAddress(address);

        final var homePeople = persons.stream()
                .map(this::parsePersonToHomePeople)
                .filter(Objects::nonNull)
                .toList();

        return new HomeFire(homePeople, firestationNumber);
    }

    private HomePeople parsePersonToHomePeople(final Person person) {
        try {
            final var medicalRecord = medicalService.getMedicalRecord(person.firstName(), person.lastName());
            return new HomePeople(person.firstName(), person.lastName(), person.phone(), medicalRecord.medications(), medicalRecord.allergies());
        } catch (final UnknownMedicalRecord unknownMedicalRecord) {
            LOGGER.warn(UNABLE_TO_FIND_MEDICAL_RECORD_FOR, person.firstName(), person.lastName());
            return null;
        }
    }

    @Override
    public Map<String, CitizenMedicalHistory[]> getCitizenServedByStations(String[] stations) throws UnknownFirestation {
        final Map<String, CitizenMedicalHistory[]> citizenMedicalHistories = new HashMap<>();

        final var addressesCoveredByStations = getCoveredAddressByStations(stations);

        for (final var address : addressesCoveredByStations) {
            final var persons = personService.getPersonByAddress(address);
            final var citizens = getPersonsInsideHouse(persons, address);

            citizenMedicalHistories.put(address, citizens);
        }

        return citizenMedicalHistories;
    }

    private CitizenMedicalHistory[] getPersonsInsideHouse(final List<Person> persons, final String address) {
        return persons.stream()
                .filter(person -> person.address().equals(address))
                .map(person -> {
                    try {
                        final var medicalHistory = medicalService.getMedicalRecord(person.firstName(), person.lastName());
                        final var age = getAge(person);
                        return new CitizenMedicalHistory(person.firstName(), person.lastName(), person.phone(), age, medicalHistory.medications(), medicalHistory.allergies());
                    } catch (UnknownMedicalRecord unknownMedicalRecord) {
                        LOGGER.warn(UNABLE_TO_FIND_MEDICAL_RECORD_FOR, person.firstName(), person.lastName(), address);
                        return null;
                    }
                }).filter(Objects::nonNull)
                .toArray(CitizenMedicalHistory[]::new);
    }

    private List<String> getCoveredAddressByStations(final String[] stations) throws UnknownFirestation {
        final List<String> stationsAddress = new ArrayList<>();
        for (final var station : stations) {
            final var currentStationAddresses = firestationService.getFirestationAddressByStationNumber(station);
            stationsAddress.addAll(currentStationAddresses);
        }

        return stationsAddress;
    }

    @Override
    public Firestation createFirestation(final Firestation firestation) throws FirestationAlreadyExist {
        return firestationService.createFirestation(firestation);
    }

    @Override
    public Firestation updateFirestation(final Firestation firestation) throws UnknownFirestation {
        return firestationService.updateFirestation(firestation);
    }

    @Override
    public Firestation deleteFirestation(final Firestation firestation) throws UnknownFirestation {
        return firestationService.deleteFirestation(firestation);
    }

    @Override
    public MedicalRecord createMedicalRecord(final MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist {
        return medicalService.createMedicalRecord(medicalRecord);
    }

    @Override
    public MedicalRecord updateMedicalRecord(final MedicalRecord medicalRecord) throws UnknownMedicalRecord {
        return medicalService.updateMedicalRecord(medicalRecord);
    }

    @Override
    public MedicalRecord deleteMedicalRecord(final MedicalRecord medicalRecord) throws UnknownMedicalRecord {
        return medicalService.deleteMedicalRecord(medicalRecord);
    }
}