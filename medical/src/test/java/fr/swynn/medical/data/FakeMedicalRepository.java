package fr.swynn.medical.data;

import fr.swynn.medical.model.MedicalRecord;

import java.util.ArrayList;
import java.util.List;

public class FakeMedicalRepository implements MedicalRepository {

    @Override
    public List<MedicalRecord> getAllMedicalRecords() {
        final var records = new ArrayList<MedicalRecord>();

        records.add(new MedicalRecord("John", "Doe", "01/01/2000", new String[] {"medication1", "medication2"}, new String[] {"allergy1", "allergy2"}));
        records.add(new MedicalRecord("Jane", "Doe", "01/01/2000", new String[] {"medication1", "medication2"}, new String[] {"allergy1", "allergy2"}));
        records.add(new MedicalRecord("John", "Smith", "01/01/2000", new String[] {"medication1", "medication2"}, new String[] {"allergy1", "allergy2"}));
        records.add(new MedicalRecord("Jane", "Smith", "01/01/2000", new String[] {"medication1", "medication2"}, new String[] {"allergy1", "allergy2"}));

        return records;
    }
}
