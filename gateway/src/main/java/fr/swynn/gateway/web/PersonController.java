package fr.swynn.gateway.web;

import fr.swynn.gateway.core.Gateway;
import fr.swynn.gateway.core.GatewayPerson;
import fr.swynn.gateway.core.GatewayPersonAlreadyExist;
import fr.swynn.gateway.core.GatewayUnknownPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ServiceLoader;

@RestController
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private static final String PERSON_NOT_FOUND= "Unable to find {} {}.";
    private static final String PERSON_EXIST = "Person {} {} already exist.";

    private Gateway gateway;

    public PersonController() {
        loadGateway();
    }

    private void loadGateway() {
        final var loadedGateway = ServiceLoader.load(Gateway.class);
        gateway = loadedGateway.findFirst().orElseThrow();
    }

    @GetMapping("/person")
    public ResponseEntity<GatewayPerson> getPerson(@RequestParam("firstName") final String firstName, @RequestParam("lastName") final String lastName) {
        try {
            final var person = gateway.getPerson(firstName, lastName);
            return new ResponseEntity<>(person, HttpStatus.OK);
        } catch (final GatewayUnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<GatewayPerson> updatePerson(@RequestBody final GatewayPerson person) {
        try {
            final var updatedPerson = gateway.updatePerson(person);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (final GatewayUnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/person")
    public ResponseEntity<GatewayPerson> createPerson(@RequestBody final GatewayPerson person) {
        try {
            final var createdPerson = gateway.createPerson(person);
            return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
        } catch (final GatewayPersonAlreadyExist ex) {
            LOGGER.error(PERSON_EXIST, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
