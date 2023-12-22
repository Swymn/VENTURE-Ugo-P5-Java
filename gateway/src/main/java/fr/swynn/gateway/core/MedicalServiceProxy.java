package fr.swynn.gateway.core;

public interface MedicalServiceProxy {

    GatewayMedicalRecord createMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist;

    GatewayMedicalRecord updateMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord;

    GatewayMedicalRecord deleteMedicalRecord(GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord;
}
