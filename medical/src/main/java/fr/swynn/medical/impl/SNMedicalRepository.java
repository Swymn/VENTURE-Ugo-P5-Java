package fr.swynn.medical.impl;

import com.fasterxml.jackson.databind.JsonNode;
import fr.swynn.database.data.JsonRepository;
import fr.swynn.database.impl.JsonRepositoryImpl;
import fr.swynn.medical.data.MedicalRepository;
import fr.swynn.medical.model.MedicalRecord;

import java.sql.Date;
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

        final var birthDate = json.get("birthdate").asText();
        final var age = convertBirthDateToAge(birthDate);

        return new MedicalRecord(
                json.get("firstName").asText(),
                json.get("lastName").asText(),
                age,
                medication.toArray(new String[0]),
                allergies.toArray(new String[0]));
    }

    private String convertBirthDateToAge(final String birthDate) {
        final var currentDate = System.currentTimeMillis();

        final var birthDateSplit = birthDate.split("/");
        final var birthDateParsed = birthDateSplit[2] + "-" + birthDateSplit[0] + "-" + birthDateSplit[1];

        final var birthDateObject = Date.valueOf(birthDateParsed);
        final var birthDateInMilliseconds = birthDateObject.getTime();

        final var age = (currentDate - birthDateInMilliseconds) / 1000 / 60 / 60 / 24 / 365;

        return String.valueOf(age);
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
