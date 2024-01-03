package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPersonAlreadyExist;
import fr.swynn.persona.model.Persona;
import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.impl.PersonAlreadyExist;
import fr.swynn.persona.impl.UnknownPerson;

import java.util.ArrayList;
import java.util.List;

public class FakePersonaService implements PersonaService {

    private final List<Persona> persons;

    public FakePersonaService() {
        persons = getPersons();
    }

    private List<Persona> getPersons() {
        final List<Persona> persons = new ArrayList<>();
        persons.add(new Persona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        persons.add(new Persona("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        persons.add(new Persona("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        persons.add(new Persona("Roger", "Boyd", "1 rue de la Paix", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

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
    public List<Persona> getPersonByAddress(final String address) {
        final var personas = new ArrayList<Persona>();
        for (final var persona : persons) {
            if (persona.address().equals(address)) {
                personas.add(persona);
            }
        }

        return personas;
    }

    @Override
    public Persona deletePersona(final Persona person) throws UnknownPerson {
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
    public Persona updatePersona(final Persona person) throws UnknownPerson  {
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
    public Persona createPersona(final Persona person) throws PersonAlreadyExist {
        for (final var personaInList : persons) {
            if (personaInList.firstName().equals(person.firstName()) && personaInList.lastName().equals(person.lastName())) {
                throw new PersonAlreadyExist(person.firstName(), person.lastName());
            }
        }

        persons.add(person);
        return person;
    }
}
