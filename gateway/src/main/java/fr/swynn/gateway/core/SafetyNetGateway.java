package fr.swynn.gateway.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class SafetyNetGateway implements Gateway {

    private static final Logger LOGGER;
    private static final String GATEWAY_LOADED_WITH_PROXY = "Gateway loaded with proxy: {}";

    private PersonServiceProxy personProxy;
    private FirestationServiceProxy firestationProxy;
    private MedicalServiceProxy medicalProxy;

    static {
        LOGGER = LoggerFactory.getLogger(SafetyNetGateway.class);
    }

    public SafetyNetGateway() {
        loadPersonProxy();
        loadFirestationProxy();
        loadMedicalProxy();
    }

    private void loadPersonProxy() {
        final var proxy = ServiceLoader.load(PersonServiceProxy.class);
        personProxy = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, personProxy.getClass().getName());
    }

    private void loadFirestationProxy() {
        final var proxy = ServiceLoader.load(FirestationServiceProxy.class);
        firestationProxy = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, firestationProxy.getClass().getName());
    }

    private void loadMedicalProxy() {
        final var proxy = ServiceLoader.load(MedicalServiceProxy.class);
        medicalProxy = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, medicalProxy.getClass().getName());
    }

    @Override
    public GatewayPersona deletePerson(final GatewayPersona person) throws GatewayUnknownPerson {
        return personProxy.deletePerson(person);
    }

    @Override
    public GatewayPersona updatePerson(final GatewayPersona person) throws GatewayUnknownPerson {
        return personProxy.updatePerson(person);
    }

    @Override
    public GatewayPersona createPerson(final GatewayPersona person) throws GatewayPersonAlreadyExist {
        return personProxy.createPerson(person);
    }

    @Override
    public GatewayFirestation createFirestation(final GatewayFirestation firestation) throws GatewayFirestationAlreadyExist {
        return firestationProxy.createFirestation(firestation);
    }

    @Override
    public GatewayFirestation updateFirestation(final GatewayFirestation firestation) throws GatewayUnknownFirestation {
        return firestationProxy.updateFirestation(firestation);
    }

    @Override
    public GatewayFirestation deleteFirestation(final GatewayFirestation firestation) throws GatewayUnknownFirestation {
        return firestationProxy.deleteFirestation(firestation);
    }

    @Override
    public GatewayMedicalRecord createMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist {
        return medicalProxy.createMedicalRecord(medicalRecord);
    }

    @Override
    public GatewayMedicalRecord updateMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        return medicalProxy.updateMedicalRecord(medicalRecord);
    }

    @Override
    public GatewayMedicalRecord deleteMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        return medicalProxy.deleteMedicalRecord(medicalRecord);
    }
}
