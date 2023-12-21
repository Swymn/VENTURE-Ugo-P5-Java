package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPersona;

public class GatewayObjectMapper {

    public GatewayPersona map(final fr.swynn.persona.model.Persona persona) {
        return new GatewayPersona(persona.firstName(), persona.lastName(), persona.address(), persona.city(), persona.zip(), persona.phone(), persona.email());
    }

    public fr.swynn.persona.model.Persona map(final GatewayPersona person) {
        return new fr.swynn.persona.model.Persona(person.firstName(), person.lastName(), person.address(), person.city(), person.zip(), person.phone(), person.email());
    }
}
