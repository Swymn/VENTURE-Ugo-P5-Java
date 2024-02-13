package fr.swynn.service;

import fr.swynn.core.PersonRepository;
import fr.swynn.core.PersonService;
import fr.swynn.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class SafetyNetPersonService implements PersonService {
    private static final Logger LOGGER;
    private static final String SERVICE_LOADED;

    private PersonRepository repository;

    private final List<Person> persons;

    static {
        LOGGER = LoggerFactory.getLogger(PersonService.class);
        SERVICE_LOADED = "Person service is loaded.";
    }

    public SafetyNetPersonService() {
        loadPersonRepository();

        persons = repository.getAllPerson();
        LOGGER.info(SERVICE_LOADED);
    }

    private void loadPersonRepository() {
        final var personRepo = ServiceLoader.load(PersonRepository.class);
        repository = personRepo.findFirst().orElseThrow();
    }

    @Override
    public List<String> getCommunityEmail(final String city) {
        return persons.stream()
                .filter(person -> person.city().equals(city))
                .map(Person::email)
                .toList();
    }

    @Override
    public List<Person> getPersonByFirstAndLastName(final String firstName, final String lastName) {
        return persons.stream()
                .filter(person -> person.firstName().equals(firstName) && person.lastName().equals(lastName))
                .toList();
    }

    @Override
    public List<Person> getPersonByAddress(final String address) {
        return persons.stream()
                .filter(person -> person.address().equals(address))
                .toList();
    }

    @Override
    public Person deletePerson(final Person person) throws UnknownPerson {
        for (int i = 0; i < persons.size(); i++) {
            final var personInList = persons.get(i);
            if (person.equals(personInList)) {
                persons.remove(i);
                return person;
            }
        }
        throw new UnknownPerson(person.firstName(), person.lastName());
    }

    @Override
    public Person updatePerson(final Person person) throws UnknownPerson {
        for (int i = 0; i < persons.size(); i++) {
            final var personInList = persons.get(i);
            if (person.equals(personInList)) {
                persons.set(i, person);
                return person;
            }
        }
        throw new UnknownPerson(person.firstName(), person.lastName());
    }

    @Override
    public Person createPerson(final Person person) throws PersonAlreadyExist {
        for (final var personInList : persons) {
            if (person.equals(personInList)) {
                throw new PersonAlreadyExist(person.firstName(), person.lastName());
            }
        }
        persons.add(person);
        return person;
    }
}
