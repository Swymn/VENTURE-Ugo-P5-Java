package fr.swynn.controller;

import fr.swynn.exception.MedicalRecordAlreadyExist;
import fr.swynn.exception.UnknownMedicalRecord;
import fr.swynn.gateway.Gateway;
import fr.swynn.gateway.GatewayProxy;
import fr.swynn.model.MedicalRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ServiceLoader;

@RestController
public class MedicalController {

    private Gateway gateway;

    public MedicalController() {
        loadGateway();
    }

    private void loadGateway() {
        final var gatewayLoader = ServiceLoader.load(GatewayProxy.class);
        final var gatewayProxy = gatewayLoader.findFirst().orElseThrow();
        gateway = gatewayProxy.getGateway();
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
        try {
            final var createdMedicalRecord = gateway.createMedicalRecord(medicalRecord);
            return new ResponseEntity<>(createdMedicalRecord, HttpStatus.CREATED);
        } catch (final MedicalRecordAlreadyExist e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
        try {
            final var updatedMedicalRecord = gateway.updateMedicalRecord(medicalRecord);
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
        } catch (final UnknownMedicalRecord e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<MedicalRecord> deleteMedicalRecord(@RequestBody final MedicalRecord medicalRecord) {
        try {
            final var deletedMedicalRecord = gateway.deleteMedicalRecord(medicalRecord);
            return new ResponseEntity<>(deletedMedicalRecord, HttpStatus.OK);
        } catch (final UnknownMedicalRecord e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
