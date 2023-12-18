package fr.swynn.persona.impl;

import fr.swynn.persona.data.PersonaRepository;
import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.model.Persona;

import java.util.List;
import java.util.ServiceLoader;

public class SNPersonaService implements PersonaService {

    private PersonaRepository repository;

    private final List<Persona> personas;

    public SNPersonaService() {
        loadPersonaRepository();

        personas = repository.getAllPersona();
    }

    private void loadPersonaRepository() {
        final var personaRepo = ServiceLoader.load(PersonaRepository.class);
        repository = personaRepo.findFirst().orElseThrow();
    }

    @Override
    public Persona getPersona(final String firstName, final String lastName) throws UnknownPerson {
        for (final var persona : personas) {
            if (persona.firstName().equals(firstName) && persona.lastName().equals(lastName)) {
                return persona;
            }
        }
        throw new UnknownPerson(firstName, lastName);
    }

    @Override
    public Persona updatePersona(final Persona persona) throws UnknownPerson {
        for (int i = 0; i < personas.size(); i++) {
            final var personaInList = personas.get(i);
            if (personaInList.firstName().equals(persona.firstName()) && personaInList.lastName().equals(persona.lastName())) {
                personas.set(i, persona);
                return persona;
            }
        }
        throw new UnknownPerson(persona.firstName(), persona.lastName());
    }

    @Override
    public Persona createPersona(final Persona persona) throws PersonAlreadyExist {
        for (final var personaInList : personas) {
            if (personaInList.firstName().equals(persona.firstName()) && personaInList.lastName().equals(persona.lastName())) {
                throw new PersonAlreadyExist(persona.firstName(), persona.lastName());
            }
        }

        personas.add(persona);
        return persona;
    }
}
