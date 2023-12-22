package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPersona;
import fr.swynn.gateway.core.GatewayPersonAlreadyExist;
import fr.swynn.gateway.core.GatewayUnknownPerson;
import fr.swynn.gateway.core.PersonServiceProxy;
import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.impl.PersonAlreadyExist;
import fr.swynn.persona.impl.UnknownPerson;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class GatewayToPersonService implements PersonServiceProxy {

    private final GatewayObjectMapper mapper;
    private PersonaService personaService;

    public GatewayToPersonService() {
        loadPersonaService();

        mapper = new GatewayObjectMapper();
    }

    private void loadPersonaService() {
        personaService = SafetyNetApplication.getPersonaService();
    }

    @Override
    public List<GatewayPersona> getPersonByAddress(final String address) {
        final var personas = personaService.getPersonByAddress(address);
        final var mappedPersonas = new ArrayList<GatewayPersona>();
        for (final var persona : personas) {
            mappedPersonas.add(mapper.map(persona));
        }
        return mappedPersonas;
    }

    @Override
    public GatewayPersona deletePerson(final GatewayPersona person) throws GatewayUnknownPerson {
        try {
            final var persona = mapper.map(person);
            final var deletedPersona = personaService.deletePersona(persona);
            return mapper.map(deletedPersona);
        } catch (final UnknownPerson ex) {
            throw new GatewayUnknownPerson(ex.getFirstName(), ex.getLastName());
        }
    }

    @Override
    public GatewayPersona updatePerson(final GatewayPersona person) throws GatewayUnknownPerson {
        try {
            final var persona = mapper.map(person);
            final var updatedPerson = personaService.updatePersona(persona);
            return mapper.map(updatedPerson);
        } catch (final UnknownPerson ex) {
            throw new GatewayUnknownPerson(ex.getFirstName(), ex.getLastName());
        }
    }

    @Override
    public GatewayPersona createPerson(final GatewayPersona person) throws GatewayPersonAlreadyExist {
        try {
            final var persona = mapper.map(person);
            final var createdPerson = personaService.createPersona(persona);
            return mapper.map(createdPerson);
        } catch (final PersonAlreadyExist ex) {
            throw new GatewayPersonAlreadyExist(ex.getFirstName(), ex.getLastName());
        }
    }
}
