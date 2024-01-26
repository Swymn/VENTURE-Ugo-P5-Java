package fr.swynn.core;

import fr.swynn.model.MedicalRecord;

import java.util.List;

public interface MedicalRepository {

    List<MedicalRecord> getAllMedicalRecords();
}
