package fr.swynn.medical.data;

import fr.swynn.medical.model.MedicalRecord;

import java.util.List;

public interface MedicalRepository {

    List<MedicalRecord> getAllMedicalRecords();
}
