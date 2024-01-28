package core;

import fr.swynn.core.*;
import fr.swynn.model.Firestation;
import fr.swynn.model.MedicalRecord;
import fr.swynn.model.Person;
import fr.swynn.service.*;

import java.util.ArrayList;
import java.util.List;


public class FakeGateway implements Gateway {

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
    public List<Person> getPersonByStationNumber(final String station) throws UnknownFirestation {
        final var stationsAddress = new ArrayList<String>();
        for (final var firestation : firestations) {
            if (firestation.station().equals(station)) {
                stationsAddress.add(firestation.address());
            }
        }

        if (stationsAddress.isEmpty()) throw new UnknownFirestation(station);

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
}

