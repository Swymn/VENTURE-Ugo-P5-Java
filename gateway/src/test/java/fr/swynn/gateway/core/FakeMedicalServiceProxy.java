package fr.swynn.gateway.core;

import java.util.ArrayList;
import java.util.List;

public class FakeMedicalServiceProxy implements MedicalServiceProxy {

    private final List<GatewayMedicalRecord> medicalRecords;

    public FakeMedicalServiceProxy() {
        medicalRecords = getMedicalRecords();
    }

    private List<GatewayMedicalRecord> getMedicalRecords() {
        final var medicalRecords = new ArrayList<GatewayMedicalRecord>();

        medicalRecords.add(new GatewayMedicalRecord("John", "Doe", "03/06/1984", new String[]{"aznol:350mg", "hydrapermazol:100mg"}, new String[]{"nillacilan"}));
        medicalRecords.add(new GatewayMedicalRecord("Jacob", "Boyd", "03/06/1989", new String[]{"pharmacol:5000mg", "terazine:10mg", "noznazol:250mg"}, new String[]{}));
        medicalRecords.add(new GatewayMedicalRecord("Tenley", "Boyd", "03/06/1989", new String[]{"noxidian:100mg", "pharmacol:7500mg", "hydrapermazol:100mg", "pharmacol:1000mg"}, new String[]{"nillacilan"}));
        medicalRecords.add(new GatewayMedicalRecord("Roger", "Boyd", "03/06/1989", new String[]{"naproxen:1000mg", "pharmacol:2500mg", "terazine:500mg", "noznazol:250mg"}, new String[]{"nillacilan"}));

        return medicalRecords;
    }

    @Override
    public GatewayMedicalRecord createMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayMedicalRecordAlreadyExist {
        for (final var medicalRecordInList : medicalRecords) {
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                throw new GatewayMedicalRecordAlreadyExist(medicalRecord.firstName(), medicalRecord.lastName());
            }
        }

        medicalRecords.add(medicalRecord);
        return medicalRecord;
    }

    @Override
    public GatewayMedicalRecord updateMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.set(i, medicalRecord);
                return medicalRecord;
            }
        }

        throw new GatewayUnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }

    @Override
    public GatewayMedicalRecord deleteMedicalRecord(final GatewayMedicalRecord medicalRecord) throws GatewayUnknownMedicalRecord {
        for (int i = 0; i < medicalRecords.size(); i++) {
            final var medicalRecordInList = medicalRecords.get(i);
            if (medicalRecordInList.firstName().equals(medicalRecord.firstName()) && medicalRecordInList.lastName().equals(medicalRecord.lastName())) {
                medicalRecords.remove(i);
                return medicalRecord;
            }
        }

        throw new GatewayUnknownMedicalRecord(medicalRecord.firstName(), medicalRecord.lastName());
    }
}
