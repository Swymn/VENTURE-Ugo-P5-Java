package fr.swynn.gateway.core;

import java.util.ArrayList;
import java.util.List;

public class FakePersonServiceProxy implements PersonServiceProxy {

    private final List<GatewayPersona> persons;

    public FakePersonServiceProxy() {
        persons = getPersons();
    }

    private List<GatewayPersona> getPersons() {
        final List<GatewayPersona> gatewayPersons = new ArrayList<>();
        gatewayPersons.add(new GatewayPersona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        gatewayPersons.add(new GatewayPersona("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        gatewayPersons.add(new GatewayPersona("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        gatewayPersons.add(new GatewayPersona("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

        return gatewayPersons;
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
}
