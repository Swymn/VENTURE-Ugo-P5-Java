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
}
