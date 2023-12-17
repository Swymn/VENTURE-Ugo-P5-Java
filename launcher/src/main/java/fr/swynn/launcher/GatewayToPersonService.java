package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPerson;
import fr.swynn.gateway.core.GatewayUnknownPerson;
import fr.swynn.gateway.core.PersonServiceProxy;
import fr.swynn.persona.data.PersonaService;
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
    public GatewayPerson getPersona(String firstName, String lastName) throws GatewayUnknownPerson {
        try {
            final var person = personaService.getPersona(firstName, lastName);
            return mapper.map(person);
        } catch (UnknownPerson e) {
            throw new GatewayUnknownPerson(firstName, lastName);
        }
    }
}
