package fr.swynn.service.impl;

import fr.swynn.repository.MedicalRepository;
import fr.swynn.model.MedicalRecord;
import fr.swynn.exception.MedicalRecordAlreadyExist;
import fr.swynn.service.MedicalService;
import fr.swynn.exception.UnknownMedicalRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ServiceLoader;

public class SafetyNetMedicalService implements MedicalService {
    private static final Logger LOGGER;
    private static final String SERVICE_LOADED;

    private final List<MedicalRecord> medicalRecords;
    private MedicalRepository repository;

    static {
        LOGGER = LoggerFactory.getLogger(SafetyNetMedicalService.class);
        SERVICE_LOADED = "Medical service is loaded.";
    }

    public SafetyNetMedicalService() {
        loadMedicalRepository();

        medicalRecords = repository.getAllMedicalRecords();
        LOGGER.info(SERVICE_LOADED);
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

    @Override
    public MedicalRecord getMedicalRecord(final String firstName, final String lastName) throws UnknownMedicalRecord {
        return medicalRecords.stream()
                .filter(medicalRecord -> medicalRecord.firstName().equals(firstName) && medicalRecord.lastName().equals(lastName))
                .findFirst()
                .orElseThrow(() -> new UnknownMedicalRecord(firstName, lastName));
    }
}