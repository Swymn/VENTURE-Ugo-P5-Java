package fr.swynn.gateway.core;

import java.util.ArrayList;
import java.util.List;

public class FakeGateway implements Gateway {

    private final List<GatewayPersona> persons;
    private final List<GatewayFirestation> firestations;
    private final List<GatewayMedicalRecord> medicalRecords;

    public FakeGateway() {
        persons = getPersons();
        firestations = getFirestations();
        medicalRecords = getMedicalRecords();
    }

    private List<GatewayPersona> getPersons() {
        final List<GatewayPersona> gatewayPersons = new ArrayList<>();
        gatewayPersons.add(new GatewayPersona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        gatewayPersons.add(new GatewayPersona("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        gatewayPersons.add(new GatewayPersona("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        gatewayPersons.add(new GatewayPersona("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

        return gatewayPersons;
    }

    private List<GatewayFirestation> getFirestations() {
        final List<GatewayFirestation> gatewayFirestations = new ArrayList<>();
        gatewayFirestations.add(new GatewayFirestation("1509 Culver St", "3"));
        gatewayFirestations.add(new GatewayFirestation("29 15th St", "2"));
        gatewayFirestations.add(new GatewayFirestation("834 Binoc Ave", "3"));
        gatewayFirestations.add(new GatewayFirestation("644 Gershwin Cir", "1"));
        gatewayFirestations.add(new GatewayFirestation("748 Townings Dr", "3"));
        gatewayFirestations.add(new GatewayFirestation("112 Steppes Pl", "3"));

        return gatewayFirestations;
    }

    private List<GatewayMedicalRecord> getMedicalRecords() {
        final var medicalRecords = new ArrayList<GatewayMedicalRecord>();

        medicalRecords.add(new GatewayMedicalRecord("John", "Doe", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}));
        medicalRecords.add(new GatewayMedicalRecord("Jacob", "Boyd", "03/06/1989", new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"}, new String[]{}));
        medicalRecords.add(new GatewayMedicalRecord("Tenley", "Boyd", "03/06/1989", new String[]{"noxidian:100mg", "pharmacol:7500mg", "hydrapermazol:100mg", "pharmacol:1000mg"}, new String[]{"nillacilan"}));
        medicalRecords.add(new GatewayMedicalRecord("Roger", "Boyd", "03/06/1989", new String[]{"naproxen:1000mg", "pharmacol:2500mg", "terazine:500mg", "noznazol:250mg"}, new String[]{"nillacilan"}));

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
    public GatewayPersona deletePerson(final GatewayPersona person) throws GatewayUnknownPerson {
        for (int i = 0; i < persons.size(); i++) {
            final var personaInList = persons.get(i);
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                persons.remove(i);
                return person;
            }
        }

        throw new GatewayUnknownPerson(person.firstName(), person.lastName());
    }

    @Override
    public GatewayPersona updatePerson(final GatewayPersona person) throws GatewayUnknownPerson  {
        for (int i = 0; i < persons.size(); i++) {
            final var personaInList = persons.get(i);
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                persons.set(i, person);
                return person;
            }
        }
        throw new GatewayUnknownPerson(person.firstName(), person.lastName());
    }

    @Override
    public GatewayPersona createPerson(final GatewayPersona person) throws GatewayPersonAlreadyExist {
        for (final var personaInList : persons) {
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                throw new GatewayPersonAlreadyExist(person.firstName(), person.lastName());
            }
        }

        persons.add(person);
        return person;
    }

    @Override
    public List<GatewayPersona> getPersonByStationNumber(final String station) throws GatewayUnknownFirestation {
        final var stationsAddress = new ArrayList<String>();
        for (final var firestation : firestations) {
            if (firestation.station().equals(station)) {
                stationsAddress.add(firestation.address());
            }
        }

        if (stationsAddress.isEmpty()) throw new GatewayUnknownFirestation(station);

        final var personas = new ArrayList<GatewayPersona>();
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
    public GatewayFirestation createFirestation(final GatewayFirestation firestation) throws GatewayFirestationAlreadyExist {
        for (final var firestationInList : firestations) {
            if (firestationInList.address().equals(firestation.address())) {
                throw new GatewayFirestationAlreadyExist(firestation.address());
            }
        }

        firestations.add(firestation);
        return firestation;
    }

    @Override
    public GatewayFirestation updateFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address())) {
                firestations.set(i, firestation);
                return firestation;
            }
        }

        throw new GatewayUnknownFirestation(firestation.address());
    }

    @Override
    public GatewayFirestation deleteFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address())) {
                firestations.remove(i);
                return firestation;
            }
        }

        throw new GatewayUnknownFirestation(firestation.address());
    }

    @Override
    public GatewayMedicalRecord createMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist {
        for (final var medicalRecordInList : medicalRecords) {
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                throw new GatewayMedicalRecordAlreadyExist(medicalRecord.firstName(), medicalRecord.lastName());
            }
        }

        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public GatewayMedicalRecord updateMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.set(i, medicalRecord);
                return medicalRecord;
            }
        }

        throw new GatewayUnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }

    @Override
    public GatewayMedicalRecord deleteMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.remove(i);
                return medicalRecord;
            }
        }

        throw new GatewayUnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }
}
