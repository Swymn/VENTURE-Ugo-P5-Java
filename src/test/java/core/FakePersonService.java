package core;

import fr.swynn.core.PersonAlreadyExist;
import fr.swynn.core.PersonService;
import fr.swynn.core.UnknownPerson;
import fr.swynn.model.Person;

import java.util.ArrayList;
import java.util.List;

public class FakePersonService implements PersonService {

    private final List<Person> persons;

    public FakePersonService() {
        this.persons = getPersons();
    }

    private List<Person> getPersons() {
        final List<Person> gatewayPersons = new ArrayList<>();
        gatewayPersons.add(new Person("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        gatewayPersons.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        gatewayPersons.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        gatewayPersons.add(new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

        return gatewayPersons;
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
    public List<Person> getPersonByAddress(final String address) {
        return persons.stream().filter(person -> person.address().equals(address)).toList();
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
    public Person updatePerson(final Person person) throws UnknownPerson  {
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
                throw new PersonAlreadyExist(person.firstName(), person.lastName());
            }
        }

        persons.add(person);
        return person;
    }
}
