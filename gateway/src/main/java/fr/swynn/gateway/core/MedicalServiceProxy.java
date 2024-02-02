package fr.swynn.gateway.core;

import java.util.List;

public interface MedicalServiceProxy {
    List<GatewayMedicalRecord> getMedicalRecords(String firstName, String lastName);

    GatewayMedicalRecord createMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist;

    GatewayMedicalRecord updateMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord;

    GatewayMedicalRecord deleteMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord;
}
