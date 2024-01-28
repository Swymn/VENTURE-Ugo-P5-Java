package fr.swynn.gateway;

import fr.swynn.core.*;
import fr.swynn.model.Firestation;
import fr.swynn.model.Person;
import fr.swynn.service.FirestationAlreadyExist;
import fr.swynn.service.UnknownFirestation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/firestation")
    public ResponseEntity<List<Person>> getPersonByStationNumber(@RequestParam("stationNumber") final String station) {
        try {
            final var personas = gateway.getPersonByStationNumber(station);
            return new ResponseEntity<>(personas, HttpStatus.OK);
        } catch (final UnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
