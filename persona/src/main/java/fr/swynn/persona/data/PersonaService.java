package fr.swynn.persona.data;

import fr.swynn.persona.impl.PersonAlreadyExist;
import fr.swynn.persona.impl.UnknownPerson;
import fr.swynn.persona.model.Persona;

public interface PersonaService {

    Persona deletePersona(Persona persona) throws UnknownPerson;

    Persona updatePersona(Persona persona) throws UnknownPerson;

    Persona createPersona(Persona persona) throws PersonAlreadyExist;

}
