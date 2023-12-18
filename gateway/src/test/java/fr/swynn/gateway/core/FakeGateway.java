package fr.swynn.gateway.core;

import java.util.ArrayList;
import java.util.List;

public class FakeGateway implements Gateway {

    private final List<GatewayPerson> persons;

    public FakeGateway() {
        persons = getPersons();
    }

    private List<GatewayPerson> getPersons() {
        final List<GatewayPerson> gatewayPersons = new ArrayList<>();
        gatewayPersons.add(new GatewayPerson("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        gatewayPersons.add(new GatewayPerson("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        gatewayPersons.add(new GatewayPerson("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        gatewayPersons.add(new GatewayPerson("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

        return gatewayPersons;
    }

    @Override
    public GatewayPerson getPerson(String firstName, String lastName) throws GatewayUnknownPerson {
        for (final var person : persons) {
            if (person.firstName().equals(firstName) && person.lastName().equals(lastName)) {
                return person;
            }
        }

        throw new GatewayUnknownPerson(firstName, lastName);
    }

    @Override
    public GatewayPerson updatePerson(GatewayPerson person) throws GatewayUnknownPerson  {
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
    public GatewayPerson createPerson(GatewayPerson person) throws GatewayPersonAlreadyExist {
        for (final var personaInList : persons) {
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                throw new GatewayPersonAlreadyExist(person.firstName(), person.lastName());
            }
        }

        persons.add(person);
        return person;
    }
}
