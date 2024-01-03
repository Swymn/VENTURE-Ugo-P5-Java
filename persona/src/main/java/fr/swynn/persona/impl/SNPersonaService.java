package fr.swynn.persona.impl;

import fr.swynn.persona.data.PersonaRepository;
import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.model.Persona;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class SNPersonaService implements PersonaService {

    private static final Logger LOGGER;
    private static final String SERVICE_LOADED;

    private PersonaRepository repository;

    private final List<Persona> personas;

    static {
        LOGGER = LoggerFactory.getLogger(SNPersonaService.class);
        SERVICE_LOADED = "Persona service is loaded.";
    }

    public SNPersonaService() {
        loadPersonaRepository();

        personas = repository.getAllPersona();
        LOGGER.info(SERVICE_LOADED);
    }

    private void loadPersonaRepository() {
        final var personaRepo = ServiceLoader.load(PersonaRepository.class);
        repository = personaRepo.findFirst().orElseThrow();
    }

    @Override
    public List<Persona> getPersonByAddress(String address) {
        return personas.stream()
                .filter(persona -> persona.address().equals(address))
                .toList();
    }

    @Override
    public Persona deletePersona(final Persona persona) throws UnknownPerson {
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
    public Persona updatePersona(final Persona persona) throws UnknownPerson {
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
    public Persona createPersona(final Persona persona) throws PersonAlreadyExist {
        for (final var personaInList : personas) {
            if (persona.equals(personaInList)) {
                throw new PersonAlreadyExist(persona.firstName(), persona.lastName());
            }
        }
        personas.add(persona);
        return persona;
    }
}
