package fr.swynn.medical.data;

import fr.swynn.medical.impl.MedicalRecordAlreadyExist;
import fr.swynn.medical.impl.UnknownMedicalRecord;
import fr.swynn.medical.model.MedicalRecord;

import java.util.List;

public interface MedicalService {

    List<MedicalRecord> getMedicalRecords(String firstName, String lastName);

    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist;

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

    MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

}
