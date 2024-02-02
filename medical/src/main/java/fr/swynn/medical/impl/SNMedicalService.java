package fr.swynn.medical.impl;

import fr.swynn.medical.data.MedicalRepository;
import fr.swynn.medical.data.MedicalService;
import fr.swynn.medical.model.MedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class SNMedicalService implements MedicalService {

    private static final Logger LOGGER;
    private static final String SERVICE_LOADED;

    private final List<MedicalRecord> medicalRecords;
    private MedicalRepository repository;

    static {
        LOGGER = LoggerFactory.getLogger(SNMedicalService.class);
        SERVICE_LOADED = "Medical service is loaded.";
    }

    public SNMedicalService() {
        loadMedicalRepository();

        medicalRecords = repository.getAllMedicalRecords();
        LOGGER.info(SERVICE_LOADED);
    }

    private void loadMedicalRepository() {
        final var loadedRepository = ServiceLoader.load(MedicalRepository.class);
        repository = loadedRepository.findFirst().orElseThrow();
    }

    @Override
    public List<MedicalRecord> getMedicalRecords(final String firstName, final String lastName) {
        // TODO: Verify if it the actual behavior
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.firstName().equals(firstName) && medicalRecord.lastName().equals(lastName))
                .toList();
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