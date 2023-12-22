package fr.swynn.medical.impl;

import fr.swynn.medical.data.MedicalRepository;
import fr.swynn.medical.data.MedicalService;
import fr.swynn.medical.model.MedicalRecord;

import java.util.List;
import java.util.ServiceLoader;

public class SNMedicalService implements MedicalService {

    private final List<MedicalRecord> medicalRecords;
    private MedicalRepository repository;

    public SNMedicalService() {
        loadMedicalRepository();

        medicalRecords = repository.getAllMedicalRecords();
    }

    private void loadMedicalRepository() {
        final var loadedRepository = ServiceLoader.load(MedicalRepository.class);
        repository = loadedRepository.findFirst().orElseThrow();
    }

    @Override
    public MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist {
        for (final var medicalRecordInList : medicalRecords) {
            if (medicalRecord.equals(medicalRecordInList)) {
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
            if (medicalRecordInList.equals(medicalRecord)) {
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
            if (medicalRecordInList.equals(medicalRecord)) {
                medicalRecords.remove(i);
                return medicalRecord;
            }
        }
        throw new UnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }
}
