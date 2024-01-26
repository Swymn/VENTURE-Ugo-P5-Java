package fr.swynn.core;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.model.Firestation;

import java.util.ArrayList;
import java.util.List;

public class DefaultFirestationRepository implements FirestationRepository {

    private static final String DATA_FILE = "data.json";
    private static final String KEY = "firestations";
    private final JsonRepository jsonRepository;

    public DefaultFirestationRepository() {
        this.jsonRepository = new DefaultJsonRepository(DATA_FILE);
    }

    @Override
    public List<Firestation> getAllfirestations() {
        final var jsonFirestations = jsonRepository.getJsonData(KEY);
        final List<Firestation> firestations = new ArrayList<>();

        for (final var jsonFirestation : jsonFirestations) {
            final var firestation = map(jsonFirestation);
            firestations.add(firestation);
        }

        return firestations;
    }

    private Firestation map(final JsonNode jsonFirestation) {
        return new Firestation(
                jsonFirestation.get("address").asText(),
                jsonFirestation.get("station").asText()
        );
    }

}
