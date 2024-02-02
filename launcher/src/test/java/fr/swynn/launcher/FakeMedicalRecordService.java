package fr.swynn.launcher;

import fr.swynn.medical.data.MedicalService;
import fr.swynn.medical.impl.MedicalRecordAlreadyExist;
import fr.swynn.medical.impl.UnknownMedicalRecord;
import fr.swynn.medical.model.MedicalRecord;

import java.util.ArrayList;
import java.util.List;

public class FakeMedicalRecordService implements MedicalService {

    private final List<MedicalRecord> medicalRecords;

    public FakeMedicalRecordService() {
        medicalRecords = getMedicalRecords();
    }

    private List<MedicalRecord> getMedicalRecords() {
        final var medicalRecords = new ArrayList<MedicalRecord>();

        medicalRecords.add(new MedicalRecord("John", "Doe", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}));
        medicalRecords.add(new MedicalRecord("Jacob", "Boyd", "03/06/1989", new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"}, new String[]{}));
        medicalRecords.add(new MedicalRecord("Tenley", "Boyd", "03/06/1989", new String[]{"noxidian:100mg", "pharmacol:7500mg", "hydrapermazol:100mg", "pharmacol:1000mg"}, new String[]{"nillacilan"}));
        medicalRecords.add(new MedicalRecord("Roger", "Boyd", "03/06/1989", new String[]{"naproxen:1000mg", "pharmacol:2500mg", "terazine:500mg", "noznazol:250mg"}, new String[]{"nillacilan"}));

        return medicalRecords;
    }

    @Override
    public List<MedicalRecord> getMedicalRecords(String firstName, String lastName) {
        // TODO: Verify if it the actual behavior
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.firstName().equals(firstName) && medicalRecord.lastName().equals(lastName))
                .toList();
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist {
        for (final var medicalRecordInList : medicalRecords) {
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                throw new MedicalRecordAlreadyExist(medicalRecord.firstName(), medicalRecord.lastName());
            }
        }

        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.set(i, medicalRecord);
                return medicalRecord;
            }
        }

        throw new UnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }

    @Override
    public MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.remove(i);
                return medicalRecord;
            }
        }

        throw new UnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }
}