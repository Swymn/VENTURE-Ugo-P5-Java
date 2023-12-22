package fr.swynn.medical.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.database.data.JsonRepository;
import fr.swynn.database.impl.JsonRepositoryImpl;
import fr.swynn.medical.data.MedicalRepository;
import fr.swynn.medical.model.MedicalRecord;

import java.util.ArrayList;
import java.util.List;

public class SNMedicalRepository implements MedicalRepository {

    private static final String DATA_FILE = "data.json";
    private static final String DATA_KEY = "medicalrecords";

    private final JsonRepository jsonRepository;

    public SNMedicalRepository() {
        this.jsonRepository = new JsonRepositoryImpl(DATA_FILE);
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
        final var list = new ArrayList<String>();
        for (int i = 0; i < array.size(); i++) {
            list.add(array.get(i).asText());
        }
        return list;
    }
}
