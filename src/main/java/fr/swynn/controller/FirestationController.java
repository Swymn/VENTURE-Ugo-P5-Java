package fr.swynn.controller;

import fr.swynn.dto.CitizenMedicalHistory;
import fr.swynn.dto.CitizenPayload;
import fr.swynn.dto.HomeFire;
import fr.swynn.gateway.Gateway;
import fr.swynn.gateway.GatewayProxy;
import fr.swynn.model.Firestation;
import fr.swynn.exception.FirestationAlreadyExist;
import fr.swynn.exception.UnknownFirestation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.ServiceLoader;

@RestController
public class FirestationController {

    private Gateway gateway;

    public FirestationController() {
        loadGateway();
    }

    private void loadGateway() {
        final var gatewayLoader = ServiceLoader.load(GatewayProxy.class);
        final var gatewayProxy = gatewayLoader.findFirst().orElseThrow();
        gateway = gatewayProxy.getGateway();
    }

    @GetMapping("/fire")
    public ResponseEntity<HomeFire> fireAlert(@RequestParam("address") final String address) {
        try {
            final var homeFire = gateway.getHomeFire(address);
            return new ResponseEntity<>(homeFire, HttpStatus.OK);
        } catch (final UnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/firestation")
    public ResponseEntity<CitizenPayload> getPersonByStationNumber(@RequestParam("stationNumber") final String station) {
        try {
            final var citizens = gateway.getPersonByStationNumber(station);
            return new ResponseEntity<>(citizens, HttpStatus.OK);
        } catch (final UnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/flood/stations")
    public ResponseEntity<Map<String, CitizenMedicalHistory[]>> getCitizenServedByStations(@RequestParam("stations") final String stations) {
        try {
            final var stationsList = stations.split(",");
            final var citizenMedicalHistories = gateway.getCitizenServedByStations(stationsList);
            return new ResponseEntity<>(citizenMedicalHistories, HttpStatus.OK);
        } catch (final UnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/phoneAlert")
    public ResponseEntity<List<String>> getPhoneListByFirestation(@RequestParam("firestation") final String station) {
        final var phoneList = gateway.getPhoneListByFirestation(station);
        return new ResponseEntity<>(phoneList, HttpStatus.OK);
    }

    @PostMapping("/firestation")
    public ResponseEntity<Firestation> createFirestation(@RequestBody() final Firestation firestation) {
        try {
            final var createdFirestation = gateway.createFirestation(firestation);
            return new ResponseEntity<>(createdFirestation, HttpStatus.CREATED);
        } catch (final FirestationAlreadyExist e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<Firestation> deleteFirestation(@RequestBody() final Firestation firestation) {
        try {
            final var deletedFirestation = gateway.deleteFirestation(firestation);
            return new ResponseEntity<>(deletedFirestation, HttpStatus.OK);
        } catch (final UnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<Firestation> updateFirestation(@RequestBody() final Firestation firestation) {
        try {
            final var updatedFirestation = gateway.updateFirestation(firestation);
            return new ResponseEntity<>(updatedFirestation, HttpStatus.OK);
        } catch (final UnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}