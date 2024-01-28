package fr.swynn.core;

import fr.swynn.model.MedicalRecord;
import fr.swynn.service.MedicalRecordAlreadyExist;
import fr.swynn.service.UnknownMedicalRecord;

public interface MedicalService {

    MedicalRecord createMedicalRecord(MedicalRecord medicalRecord) throws MedicalRecordAlreadyExist;

    MedicalRecord updateMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

    MedicalRecord deleteMedicalRecord(MedicalRecord medicalRecord) throws UnknownMedicalRecord;

    MedicalRecord getMedicalRecord(String firstName, String lastName) throws UnknownMedicalRecord;

}