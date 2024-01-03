package fr.swynn.gateway.core;

import java.util.List;

public interface Gateway {

    List<String> getCommunityEmail(String city);

    GatewayPersona deletePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona updatePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona createPerson(GatewayPersona person) throws GatewayPersonAlreadyExist;

    List<GatewayPersona> getPersonByStationNumber(String station) throws GatewayUnknownFirestation;

    GatewayFirestation createFirestation(GatewayFirestation firestation) throws GatewayFirestationAlreadyExist;

    GatewayFirestation updateFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation;

    GatewayFirestation deleteFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation;

    GatewayMedicalRecord createMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist;

    GatewayMedicalRecord updateMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord;

    GatewayMedicalRecord deleteMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord;
}
