package fr.swynn.core;

import fr.swynn.model.MedicalRecord;

public interface MedicalService {

    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist;

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

    MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

}