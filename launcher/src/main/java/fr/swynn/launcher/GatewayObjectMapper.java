package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayPerson;
import fr.swynn.persona.model.Persona;

public class GatewayObjectMapper {

    public GatewayPerson map(final Persona persona) {
        return new GatewayPerson(persona.firstName(), persona.lastName(), persona.address(), persona.city(), persona.zip(), persona.phone(), persona.email());
    }

    public Persona map(final GatewayPerson person) {
        return new Persona(person.firstName(), person.lastName(), person.address(), person.city(), person.zip(), person.phone(), person.email());
    }
}
