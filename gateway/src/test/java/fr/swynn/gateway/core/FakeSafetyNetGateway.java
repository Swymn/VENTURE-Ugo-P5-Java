package fr.swynn.gateway.core;

import java.util.List;

public class FakeSafetyNetGateway implements Gateway {

    private final List<GatewayPerson> persons;

    public FakeSafetyNetGateway() {
        persons = getPersons();
    }

    private List<GatewayPerson> getPersons() {
        return List.of(
                new GatewayPerson("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"),
                new GatewayPerson("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"),
                new GatewayPerson("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"),
                new GatewayPerson("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));
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
}
