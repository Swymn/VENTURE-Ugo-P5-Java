package fr.swynn.gateway;

import fr.swynn.core.*;
import fr.swynn.dto.ChildCitizen;
import fr.swynn.dto.Citizen;
import fr.swynn.dto.CitizenPayload;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import fr.swynn.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.ServiceLoader;

public class SafetyNetGateway implements Gateway {

    private static final Logger LOGGER;
    private static final String GATEWAY_LOADED_WITH_PROXY;
    private static final String GATEWAY_LOADED;
    private static final String DATE_FORMAT;

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
        return new Citizen(person.firstName(), person.lastName(), person.address(), person.phone());
    }

    private boolean isAdult(final Person person) {
        try {
            final var medicalRecord = medicalService.getMedicalRecord(person.firstName(), person.lastName());
            final var dateFormat = new SimpleDateFormat(DATE_FORMAT);
            final var birthDate = dateFormat.parse(medicalRecord.birthdate());

            final var currentDate = Calendar.getInstance();
            final var now = currentDate.getTime();

            long ageInMillis = now.getTime() - birthDate.getTime();
            long ageInYears = ageInMillis / (1000L * 60 * 60 * 24 * 365);

            return ageInYears > 18;
        } catch (final UnknownMedicalRecord | ParseException unknownMedicalRecord) {
            LOGGER.warn("Unable to find medical record for {} {}", person.firstName(), person.lastName());
            return false;
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
                .map(member -> new Citizen(member.firstName(), member.lastName(), member.address(), member.phone()))
                .toList();

        return new ChildCitizen(person.firstName(), person.lastName(), familyMembersWithoutChild);
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
