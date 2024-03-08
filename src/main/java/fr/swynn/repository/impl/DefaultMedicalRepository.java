package fr.swynn.repository.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.model.MedicalRecord;
import fr.swynn.repository.JsonRepository;
import fr.swynn.repository.MedicalRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class DefaultMedicalRepository implements MedicalRepository {

    private static final String DATA_KEY = "medicalrecords";

    private JsonRepository jsonRepository;

    public DefaultMedicalRepository() {
        loadJsonRepository();
    }

    private void loadJsonRepository() {
        final var proxy = ServiceLoader.load(JsonRepository.class);
        jsonRepository = proxy.findFirst().orElseThrow();
    }

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        final var jsonData = jsonRepository.getJsonData(DATA_KEY);
        final var medicalRecords = new ArrayList<MedicalRecord>();

        for (final JsonNode json : jsonData) {
            medicalRecords.add(map(json));
        }

        return medicalRecords;
    }

    private MedicalRecord map(final JsonNode json) {

        final var medication = extractArray(json, "medications");
        final var allergies = extractArray(json, "allergies");

        return new MedicalRecord(
                json.get("firstName").asText(),
                json.get("lastName").asText(),
                json.get("birthdate").asText(),
                medication.toArray(new String[0]),
                allergies.toArray(new String[0]));
    }

    private List<String> extractArray(final JsonNode json, final String key) {
        final var array = json.get(key);
        final List<String> list = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.get(i).asText());
        }
        return list;
    }

}