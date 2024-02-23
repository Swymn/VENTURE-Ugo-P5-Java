package fr.swynn.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.model.Firestation;
import fr.swynn.repository.FirestationRepository;
import fr.swynn.repository.JsonRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DefaultFirestationRepository implements FirestationRepository {
    private static final String KEY = "firestations";
    private JsonRepository jsonRepository;

    public DefaultFirestationRepository() {
        loadJsonRepository();
    }

    private void loadJsonRepository() {
        final var proxy = ServiceLoader.load(JsonRepository.class);
        jsonRepository = proxy.findFirst().orElseThrow();
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
