package fr.swynn.persona.impl;

import fr.swynn.persona.data.PersonaRepository;
import fr.swynn.persona.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class SNPersonRepository implements PersonaRepository {

    @Override
    public List<Persona> getAllPersona() {
        return new ArrayList<>();
    }
}
