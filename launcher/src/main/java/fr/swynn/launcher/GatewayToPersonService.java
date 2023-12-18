package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPerson;
import fr.swynn.gateway.core.GatewayPersonAlreadyExist;
import fr.swynn.gateway.core.GatewayUnknownPerson;
import fr.swynn.gateway.core.PersonServiceProxy;
import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.impl.PersonAlreadyExist;
import fr.swynn.persona.impl.UnknownPerson;

import java.util.ServiceLoader;

public class GatewayToPersonService implements PersonServiceProxy {

    private final GatewayObjectMapper mapper;
    private PersonaService personaService;

    public GatewayToPersonService() {
        loadPersonaService();

        mapper = new GatewayObjectMapper();
    }

    private void loadPersonaService() {
        final var service = ServiceLoader.load(PersonaService.class);
        personaService = service.findFirst().orElseThrow();
    }

    @Override
    public GatewayPerson getPerson(final String firstName, final String lastName) throws GatewayUnknownPerson {
        try {
            final var person = personaService.getPersona(firstName, lastName);
            return mapper.map(person);
        } catch (final UnknownPerson ex) {
            throw new GatewayUnknownPerson(ex.getFirstName(), ex.getLastName());
        }
    }

    @Override
    public GatewayPerson updatePerson(final GatewayPerson person) throws GatewayUnknownPerson {
        try {
            final var persona = mapper.map(person);
            final var updatedPerson = personaService.updatePersona(persona);
            return mapper.map(updatedPerson);
        } catch (final UnknownPerson ex) {
            throw new GatewayUnknownPerson(ex.getFirstName(), ex.getLastName());
        }
    }

    @Override
    public GatewayPerson createPerson(final GatewayPerson person) throws GatewayPersonAlreadyExist {
        try {
            final var persona = mapper.map(person);
            final var createdPerson = personaService.createPersona(persona);
            return mapper.map(createdPerson);
        } catch (final PersonAlreadyExist ex) {
            throw new GatewayPersonAlreadyExist(ex.getFirstName(), ex.getLastName());
        }
    }
}
