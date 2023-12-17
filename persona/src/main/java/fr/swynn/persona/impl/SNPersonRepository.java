package fr.swynn.persona.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.database.data.JsonRepository;
import fr.swynn.database.impl.JsonRepositoryImpl;
import fr.swynn.persona.data.PersonaRepository;
import fr.swynn.persona.model.Persona;

import java.util.ArrayList;
import java.util.List;

public class SNPersonRepository implements PersonaRepository {

    private static final String DATA_FILE = "data.json";
    private static final String KEY = "persons";

    private final JsonRepository jsonRepository;

    public SNPersonRepository() {
        this.jsonRepository = new JsonRepositoryImpl(DATA_FILE);
    }

    @Override
    public List<Persona> getAllPersona() {
        final var jsonPersonas = jsonRepository.getJsonData(KEY);
        final var personas = new ArrayList<Persona>();

        for (final var jsonPersona : jsonPersonas) {
            final var persona = map(jsonPersona);
            personas.add(persona);
        }

        return personas;
    }

    private Persona map(final JsonNode jsonPersona) {
        return new Persona(
                jsonPersona.get("firstName").asText(),
                jsonPersona.get("lastName").asText(),
                jsonPersona.get("address").asText(),
                jsonPersona.get("city").asText(),
                jsonPersona.get("zip").asText(),
                jsonPersona.get("phone").asText(),
                jsonPersona.get("email").asText()
        );
    }
}
