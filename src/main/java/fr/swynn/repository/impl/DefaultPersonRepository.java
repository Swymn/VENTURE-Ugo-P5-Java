package fr.swynn.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.model.Person;
import fr.swynn.repository.JsonRepository;
import fr.swynn.repository.PersonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DefaultPersonRepository implements PersonRepository {
    private static final String KEY = "persons";

    private JsonRepository jsonRepository;

    public DefaultPersonRepository() {
        loadJsonRepository();
    }

    private void loadJsonRepository() {
        jsonRepository = ServiceLoader.load(JsonRepository.class)
                .findFirst()
                .orElseThrow();
    }

    @Override
    public List<Person> getAllPerson() {
        final var jsonPersons = jsonRepository.getJsonData(KEY);
        final List<Person> personas = new ArrayList<>();

        for (final var jsonPerson : jsonPersons) {
            final var persona = map(jsonPerson);
            personas.add(persona);
        }

        return personas;
    }

    private Person map(final JsonNode jsonPerson) {
        return new Person(
                jsonPerson.get("firstName").asText(),
                jsonPerson.get("lastName").asText(),
                jsonPerson.get("address").asText(),
                jsonPerson.get("city").asText(),
                jsonPerson.get("zip").asText(),
                jsonPerson.get("phone").asText(),
                jsonPerson.get("email").asText()
        );
    }
}
