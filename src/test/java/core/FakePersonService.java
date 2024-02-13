package core;

import fr.swynn.core.PersonService;
import fr.swynn.model.Person;
import fr.swynn.service.PersonAlreadyExist;
import fr.swynn.service.UnknownPerson;

import java.util.ArrayList;
import java.util.List;

public class FakePersonService implements PersonService {

    private static List<Person> persons;

    public FakePersonService() {
        initPerson();
    }

    public static void initPerson() {
        persons = getPersons();
    }

    private static List<Person> getPersons() {
        final List<Person> persons = new ArrayList<>();
        persons.add(new Person("John", "Doe", "1509 Baylee St", "Washington",
                "15280", "841-874-6512", "john.doe@mail.com"));
        persons.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "jacob.boyd@mail.com"));
        persons.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver",
                "97451", "841-874-6512", "tenley.boyd@mail.com"));
        persons.add(new Person("Roger", "Boyd", "1 rue de la Paix", "Culver",
                "97451", "841-874-6512", "roger.boyd@mail.com"));
        persons.add(new Person("Gwen", "Boyd", "1509 Baylee St",
                "Culver", "97451", "841-874-6512", "gwen.boyd@mail.com"));
        return persons;
    }

    @Override
    public List<String> getCommunityEmail(String city) {
        final var emails = new ArrayList<String>();
        for (final var persona : persons) {
            if (persona.city().equals(city)) {
                emails.add(persona.email());
            }
        }

        return emails;
    }

    @Override
    public List<Person> getPersonByAddress(final String address) {
        final var personas = new ArrayList<Person>();
        for (final var persona : persons) {
            if (persona.address().equals(address)) {
                personas.add(persona);
            }
        }

        return personas;
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

}
