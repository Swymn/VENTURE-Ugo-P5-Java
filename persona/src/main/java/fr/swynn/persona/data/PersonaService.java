package fr.swynn.persona.data;

import fr.swynn.persona.impl.PersonAlreadyExist;
import fr.swynn.persona.impl.UnknownPerson;
import fr.swynn.persona.model.Persona;

import java.util.List;

public interface PersonaService {

    List<String> getCommunityEmail(String city);

    List<Persona> getPersonByAddress(String address);

    Persona deletePersona(Persona persona) throws UnknownPerson;

    Persona updatePersona(Persona persona) throws UnknownPerson;

    Persona createPersona(Persona persona) throws PersonAlreadyExist;

}
