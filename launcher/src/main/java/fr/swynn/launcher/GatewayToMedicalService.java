package fr.swynn.launcher;

import fr.swynn.gateway.core.GatewayMedicalRecord;
import fr.swynn.gateway.core.GatewayMedicalRecordAlreadyExist;
import fr.swynn.gateway.core.GatewayUnknownMedicalRecord;
import fr.swynn.gateway.core.MedicalServiceProxy;
import fr.swynn.medical.data.MedicalService;
import fr.swynn.medical.impl.MedicalRecordAlreadyExist;
import fr.swynn.medical.impl.UnknownMedicalRecord;
import fr.swynn.persona.data.PersonaService;

import java.util.ServiceLoader;

public class GatewayToMedicalService implements MedicalServiceProxy {

    private final GatewayObjectMapper mapper = new GatewayObjectMapper();
    private MedicalService service;

    public GatewayToMedicalService() {
        loadMedicalService();
    }

    private void loadMedicalService() {
        final var loadedService = ServiceLoader.load(MedicalService.class);
        service = loadedService.findFirst().orElseThrow();
    }

    @Override
    public GatewayMedicalRecord createMedicalRecord(final GatewayMedicalRecord gatewayMedicalRecord) throws GatewayMedicalRecordAlreadyExist {
        try {
            final var medicalRecord = mapper.map(gatewayMedicalRecord);
            final var createdMedicalRecord = service.createMedicalRecord(medicalRecord);
            return mapper.map(createdMedicalRecord);
        } catch (final MedicalRecordAlreadyExist ex) {
            throw new GatewayMedicalRecordAlreadyExist(ex.getFirstName(), ex.getLastName());
        }
    }

    @Override
    public GatewayMedicalRecord updateMedicalRecord(final GatewayMedicalRecord gatewayMedicalRecord) throws GatewayUnknownMedicalRecord {
        try {
            final var medicalRecord = mapper.map(gatewayMedicalRecord);
            final var updatedMedicalRecord = service.updateMedicalRecord(medicalRecord);
            return mapper.map(updatedMedicalRecord);
        } catch (final UnknownMedicalRecord ex) {
            throw new GatewayUnknownMedicalRecord(ex.getFirstName(), ex.getLastName());
        }
    }

    @Override
    public GatewayMedicalRecord deleteMedicalRecord(final GatewayMedicalRecord gatewayMedicalRecord) throws GatewayUnknownMedicalRecord {
        try {
            final var medicalRecord = mapper.map(gatewayMedicalRecord);
            final var deletedMedicalRecord = service.deleteMedicalRecord(medicalRecord);
            return mapper.map(deletedMedicalRecord);
        } catch (final UnknownMedicalRecord ex) {
            throw new GatewayUnknownMedicalRecord(ex.getFirstName(), ex.getLastName());
        }
    }
}
