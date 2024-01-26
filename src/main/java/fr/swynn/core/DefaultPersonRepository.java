package fr.swynn.core;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.model.Person;

import java.util.ArrayList;
import java.util.List;

public class DefaultPersonRepository implements PersonRepository {
    private static final String DATA_FILE = "data.json";
    private static final String KEY = "persons";

    private final JsonRepository jsonRepository;

    public DefaultPersonRepository() {
        this.jsonRepository = new DefaultJsonRepository(DATA_FILE);
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
