package fr.swynn.gateway.web;

import fr.swynn.gateway.core.GatewayFirestation;
import fr.swynn.gateway.core.Gateway;
import fr.swynn.gateway.core.GatewayFirestationAlreadyExist;
import fr.swynn.gateway.core.SafetyNetGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InfernoController {

    private Gateway gateway;

    public InfernoController() {
        loadGateway();
    }

    private void loadGateway() {
        gateway = new SafetyNetGateway();
    }

    @PostMapping("/firestation")
    public ResponseEntity<GatewayFirestation> createFirestation(@RequestBody() final GatewayFirestation firestation) {
        try {
            gateway.createFirestation(firestation);
            return new ResponseEntity<>(firestation, HttpStatus.CREATED);
        } catch (final GatewayFirestationAlreadyExist e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
