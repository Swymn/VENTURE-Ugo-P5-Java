package fr.swynn.core;

import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import fr.swynn.service.*;

import java.util.List;

public interface Gateway {

    List<String> getCommunityEmail(String city);

    Person deletePerson(Person person) throws UnknownPerson;

    Person updatePerson(Person person) throws UnknownPerson;

    Person createPerson(Person person) throws PersonAlreadyExist;

    List<Person> getPersonByStationNumber(String station) throws UnknownFirestation;

    Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist;

    Firestation updateFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation;

    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist;

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

    MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

}
