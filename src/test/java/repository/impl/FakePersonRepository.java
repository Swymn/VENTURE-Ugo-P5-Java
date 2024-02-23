package repository.impl;

import fr.swynn.repository.PersonRepository;
import fr.swynn.model.Person;

import java.util.ArrayList;
import java.util.List;

public class FakePersonRepository implements PersonRepository {

    @Override
    public List<Person> getAllPerson() {
        final List<Person> personas = new ArrayList<>();
        personas.add(new Person("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        personas.add(new Person("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        personas.add(new Person("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        personas.add(new Person("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

        return personas;
    }
}
