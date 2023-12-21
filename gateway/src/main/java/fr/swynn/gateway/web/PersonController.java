package fr.swynn.gateway.web;

import fr.swynn.gateway.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        gateway = new SafetyNetGateway();
    }

    @DeleteMapping("/person")
    public ResponseEntity<GatewayPersona> deletePerson(@RequestBody final GatewayPersona person) {
        try {
            final var deletedPerson = gateway.deletePerson(person);
            return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
        } catch (final GatewayUnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<GatewayPersona> updatePerson(@RequestBody final GatewayPersona person) {
        try {
            final var updatedPerson = gateway.updatePerson(person);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (final GatewayUnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/person")
    public ResponseEntity<GatewayPersona> createPerson(@RequestBody final GatewayPersona person) {
        try {
            final var createdPerson = gateway.createPerson(person);
            return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
        } catch (final GatewayPersonAlreadyExist ex) {
            LOGGER.error(PERSON_EXIST, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}
