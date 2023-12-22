package fr.swynn.gateway.web;

import fr.swynn.gateway.core.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FirestationController {

    private Gateway gateway;

    public FirestationController() {
        loadGateway();
    }

    private void loadGateway() {
        gateway = new SafetyNetGateway();
    }

    @PostMapping("/firestation")
    public ResponseEntity<GatewayFirestation> createFirestation(@RequestBody() final GatewayFirestation firestation) {
        try {
            final var createdFirestation = gateway.createFirestation(firestation);
            return new ResponseEntity<>(createdFirestation, HttpStatus.CREATED);
        } catch (final GatewayFirestationAlreadyExist e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/firestation")
    public ResponseEntity<GatewayFirestation> deleteFirestation(@RequestBody() final GatewayFirestation firestation) {
        try {
            final var deletedFirestation = gateway.deleteFirestation(firestation);
            return new ResponseEntity<>(deletedFirestation, HttpStatus.OK);
        } catch (final GatewayUnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/firestation")
    public ResponseEntity<GatewayFirestation> updateFirestation(@RequestBody() final GatewayFirestation firestation) {
        try {
            final var updatedFirestation = gateway.updateFirestation(firestation);
            return new ResponseEntity<>(updatedFirestation, HttpStatus.OK);
        } catch (final GatewayUnknownFirestation ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}