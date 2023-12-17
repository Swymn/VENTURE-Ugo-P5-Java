package fr.swynn.persona.data;

import fr.swynn.persona.impl.UnknownPerson;
import fr.swynn.persona.model.Persona;

public interface PersonaService {

    Persona getPersona(String firstName, String lastName) throws UnknownPerson;

}
