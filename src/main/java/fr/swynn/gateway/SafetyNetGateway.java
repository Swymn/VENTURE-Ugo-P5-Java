package fr.swynn.gateway;

import fr.swynn.core.*;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import fr.swynn.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class SafetyNetGateway implements Gateway {

    private static final Logger LOGGER;
    private static final String GATEWAY_LOADED_WITH_PROXY;
    private static final String GATEWAY_LOADED;

    private static Gateway instance;

    private PersonService personService;
    private FirestationService firestationService;
    private MedicalService medicalService;

    static {
        LOGGER = LoggerFactory.getLogger(SafetyNetGateway.class);
        GATEWAY_LOADED_WITH_PROXY = "Connection to Gateway loaded with {} proxy.";
        GATEWAY_LOADED = "Gateway instanced and loaded";
        instance = new SafetyNetGateway();
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
    public List<Person> getPersonByStationNumber(String station) throws UnknownFirestation {
        final var stationsAddress = firestationService.getFirestationAddressByStationNumber(station);
        final List<Person> personas = new ArrayList<>();

        for (final var address : stationsAddress) {
            final var currentPersonas = personService.getPersonByAddress(address);
            personas.addAll(currentPersonas);
        }

        return personas;
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
