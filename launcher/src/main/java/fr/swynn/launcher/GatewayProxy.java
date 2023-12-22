package fr.swynn.launcher;

import fr.swynn.gateway.core.*;

import java.util.List;

public class GatewayProxy implements Gateway {

    protected static Gateway gateway = null;

    protected static Gateway initializeGateway() {
        if (gateway == null) {
            gateway = SafetyNetApplication.getGateway();
        }
        return gateway;
    }

    @Override
    public List<GatewayPersona> getPersonByAddress(String address) {
        initializeGateway();
        return gateway.getPersonByAddress(address);
    }

    @Override
    public GatewayPersona deletePerson(GatewayPersona person) throws GatewayUnknownPerson {
        initializeGateway();
        return gateway.deletePerson(person);
    }

    @Override
    public GatewayPersona updatePerson(GatewayPersona person) throws GatewayUnknownPerson {
        initializeGateway();
        return gateway.updatePerson(person);
    }

    @Override
    public GatewayPersona createPerson(GatewayPersona person) throws GatewayPersonAlreadyExist {
        initializeGateway();
        return gateway.createPerson(person);
    }

    @Override
    public List<GatewayPersona> getPersonByStationNumber(String station) throws GatewayUnknownFirestation {
        initializeGateway();
        return gateway.getPersonByStationNumber(station);
    }

    @Override
    public List<String> getFirestationAddressByStationNumber(String station) throws GatewayUnknownFirestation {
        initializeGateway();
        return gateway.getFirestationAddressByStationNumber(station);
    }

    @Override
    public GatewayFirestation createFirestation(GatewayFirestation firestation) throws GatewayFirestationAlreadyExist {
        initializeGateway();
        return gateway.createFirestation(firestation);
    }

    @Override
    public GatewayFirestation updateFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation {
        initializeGateway();
        return gateway.updateFirestation(firestation);
    }

    @Override
    public GatewayFirestation deleteFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation {
        initializeGateway();
        return gateway.deleteFirestation(firestation);
    }

    @Override
    public GatewayMedicalRecord createMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist {
        initializeGateway();
        return gateway.createMedicalRecord(medicalRecord);
    }

    @Override
    public GatewayMedicalRecord updateMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        initializeGateway();
        return gateway.updateMedicalRecord(medicalRecord);
    }

    @Override
    public GatewayMedicalRecord deleteMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        initializeGateway();
        return gateway.deleteMedicalRecord(medicalRecord);
    }
}
