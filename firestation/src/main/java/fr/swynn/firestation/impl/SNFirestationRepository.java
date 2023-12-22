package fr.swynn.firestation.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.database.data.JsonRepository;
import fr.swynn.database.impl.JsonRepositoryImpl;
import fr.swynn.firestation.data.FirestationRepository;
import fr.swynn.firestation.model.Firestation;

import java.util.ArrayList;
import java.util.List;

public class SNFirestationRepository implements FirestationRepository {

    private static final String DATA_FILE = "data.json";
    private static final String KEY = "firestations";
    private final JsonRepository jsonRepository;

    public SNFirestationRepository() {
        this.jsonRepository = new JsonRepositoryImpl(DATA_FILE);
    }

    @Override
    public List<Firestation> getAllfirestations() {
        final var jsonFirestations = jsonRepository.getJsonData(KEY);
        final var firestations = new ArrayList<Firestation>();

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
