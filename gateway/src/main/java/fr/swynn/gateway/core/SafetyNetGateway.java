package fr.swynn.gateway.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class SafetyNetGateway implements Gateway {
    private PersonServiceProxy personProxy;
    private FirestationServiceProxy firestationProxy;
    private MedicalServiceProxy medicalProxy;

    public SafetyNetGateway() {
        loadPersonProxy();
        loadFirestationProxy();
        loadMedicalProxy();
    }

    private void loadPersonProxy() {
        final var proxy = ServiceLoader.load(PersonServiceProxy.class);
        personProxy = proxy.findFirst().orElseThrow();
    }

    private void loadFirestationProxy() {
        final var proxy = ServiceLoader.load(FirestationServiceProxy.class);
        firestationProxy = proxy.findFirst().orElseThrow();
    }

    private void loadMedicalProxy() {
        final var proxy = ServiceLoader.load(MedicalServiceProxy.class);
        medicalProxy = proxy.findFirst().orElseThrow();
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
    public List<GatewayPersona> getPersonByStationNumber(String station) throws GatewayUnknownFirestation {
        final var stationsAddress = firestationProxy.getFirestationAddressByStationNumber(station);
        final var personas = new ArrayList<GatewayPersona>();

        for (final var address : stationsAddress) {
            final var currentPersonas = personProxy.getPersonByAddress(address);
            personas.addAll(currentPersonas);
        }

        return personas;
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
