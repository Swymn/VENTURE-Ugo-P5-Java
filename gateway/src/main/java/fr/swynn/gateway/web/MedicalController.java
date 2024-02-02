package fr.swynn.gateway.web;

import fr.swynn.gateway.core.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/personInfo")
    public ResponseEntity<List<GatewayMedicalRecord>> getMedicalRecords(@RequestParam("firstName") final String firstName, @RequestParam("lastName") final String lastName) {
        final var medicalRecords = gateway.getMedicalRecords(firstName, lastName);
        return new ResponseEntity<>(medicalRecords, HttpStatus.OK);
    }

    @PostMapping("/medicalRecord")
    public ResponseEntity<GatewayMedicalRecord> createMedicalRecord(@RequestBody final GatewayMedicalRecord medicalRecord) {
        try {
            final var createdMedicalRecord = gateway.createMedicalRecord(medicalRecord);
            return new ResponseEntity<>(createdMedicalRecord, HttpStatus.CREATED);
        } catch (final GatewayMedicalRecordAlreadyExist e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/medicalRecord")
    public ResponseEntity<GatewayMedicalRecord> updateMedicalRecord(@RequestBody final GatewayMedicalRecord medicalRecord) {
        try {
            final var updatedMedicalRecord = gateway.updateMedicalRecord(medicalRecord);
            return new ResponseEntity<>(updatedMedicalRecord, HttpStatus.OK);
        } catch (final GatewayUnknownMedicalRecord e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/medicalRecord")
    public ResponseEntity<GatewayMedicalRecord> deleteMedicalRecord(@RequestBody final GatewayMedicalRecord medicalRecord) {
        try {
            final var deletedMedicalRecord = gateway.deleteMedicalRecord(medicalRecord);
            return new ResponseEntity<>(deletedMedicalRecord, HttpStatus.OK);
        } catch (final GatewayUnknownMedicalRecord e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
