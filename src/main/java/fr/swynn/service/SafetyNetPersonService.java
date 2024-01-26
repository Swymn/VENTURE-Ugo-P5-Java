package fr.swynn.service;

import fr.swynn.core.PersonAlreadyExist;
import fr.swynn.core.PersonRepository;
import fr.swynn.core.PersonService;
import fr.swynn.core.UnknownPerson;
import fr.swynn.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class SafetyNetPersonService implements PersonService {
    private static final Logger LOGGER;
    private static final String SERVICE_LOADED;

    private PersonRepository repository;

    private final List<Person> personas;

    static {
        LOGGER = LoggerFactory.getLogger(PersonService.class);
        SERVICE_LOADED = "Person service is loaded.";
    }

    public SafetyNetPersonService() {
        loadPersonRepository();

        personas = repository.getAllPerson();
        LOGGER.info(SERVICE_LOADED);
    }

    private void loadPersonRepository() {
        final var personaRepo = ServiceLoader.load(PersonRepository.class);
        repository = personaRepo.findFirst().orElseThrow();
    }

    @Override
    public List<String> getCommunityEmail(final String city) {
        return personas.stream()
                .filter(persona -> persona.city().equals(city))
                .map(Person::email)
                .toList();
    }

    @Override
    public List<Person> getPersonByAddress(String address) {
        return personas.stream()
                .filter(persona -> persona.address().equals(address))
                .toList();
    }

    @Override
    public Person deletePerson(final Person persona) throws UnknownPerson {
        for (int i = 0; i < personas.size(); i++) {
            final var personaInList = personas.get(i);
            if (persona.equals(personaInList)) {
                personas.remove(i);
                return persona;
            }
        }
        throw new UnknownPerson(persona.firstName(), persona.lastName());
    }

    @Override
    public Person updatePerson(final Person persona) throws UnknownPerson {
        for (int i = 0; i < personas.size(); i++) {
            final var personaInList = personas.get(i);
            if (persona.equals(personaInList)) {
                personas.set(i, persona);
                return persona;
            }
        }
        throw new UnknownPerson(persona.firstName(), persona.lastName());
    }

    @Override
    public Person createPerson(final Person persona) throws PersonAlreadyExist {
        for (final var personaInList : personas) {
            if (persona.equals(personaInList)) {
                throw new PersonAlreadyExist(persona.firstName(), persona.lastName());
            }
        }
        personas.add(persona);
        return persona;
    }
}
