package fr.swynn.persona.data;

import fr.swynn.persona.data.PersonaRepository;
import fr.swynn.persona.model.Persona;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FakePersonaRepository implements PersonaRepository {

    @Override
    public List<Persona> getAllPersona() {
        final List<Persona> personas = new ArrayList<>();
        personas.add(new Persona("John", "Doe", "1509 Baylee St", "Washington", "15280", "841-874-6512", "john.doe@mail.com"));
        personas.add(new Persona("Jacob", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "jacob.boyd@mail.com"));
        personas.add(new Persona("Tenley", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "tenley.boyd@mail.com"));
        personas.add(new Persona("Roger", "Boyd", "1509 Culver St", "Culver", "97451", "841-874-6512", "roger.boyd@mail.com"));

        return personas;
    }
}
