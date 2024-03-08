package fr.swynn.gateway;

import fr.swynn.dto.*;
import fr.swynn.exception.*;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;

import java.util.List;
import java.util.Map;

public interface Gateway {

    List<String> getCommunityEmail(String city);

    List<String> getPhoneListByFirestation(String station);

    List<ChildCitizen> getChildrensByAddress(String address);

    List<DetailedCitizen> getPersonByFirstAndLastName(String firstName, String lastName);

    Person deletePerson(Person person) throws UnknownPerson;

    Person updatePerson(Person person) throws UnknownPerson;

    Person createPerson(Person person) throws PersonAlreadyExist;

    HomeFire getHomeFire(String address) throws UnknownFirestation;

    CitizenPayload getPersonByStationNumber(String station) throws UnknownFirestation;

    Map<String, CitizenMedicalHistory[]> getCitizenServedByStations(String[] stations) throws UnknownFirestation;

    Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist;

    Firestation updateFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation;

    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist;

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

    MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

}