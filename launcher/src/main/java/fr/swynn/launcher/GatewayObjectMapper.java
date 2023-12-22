package fr.swynn.launcher;

import fr.swynn.firestation.model.Firestation;
import fr.swynn.gateway.core.GatewayFirestation;
import fr.swynn.gateway.core.GatewayPersona;
import fr.swynn.persona.model.Persona;

public class GatewayObjectMapper {

    public GatewayPersona map(final Persona persona) {
        return new GatewayPersona(persona.firstName(), persona.lastName(), persona.address(), persona.city(), persona.zip(), persona.phone(), persona.email());
    }

    public Persona map(final GatewayPersona person) {
        return new Persona(person.firstName(), person.lastName(), person.address(), person.city(), person.zip(), person.phone(), person.email());
    }

    public GatewayFirestation map(final Firestation firestation) {
        return new GatewayFirestation(firestation.address(), firestation.station());
    }

    public Firestation map(final GatewayFirestation firestation) {
        return new Firestation(firestation.address(), firestation.station());
    }
}
