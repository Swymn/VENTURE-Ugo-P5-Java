package fr.swynn.gateway;

import fr.swynn.core.PersonAlreadyExist;
import fr.swynn.core.PersonService;
import fr.swynn.core.UnknownPerson;
import fr.swynn.model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.ServiceLoader;

@RestController
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private static final String PERSON_NOT_FOUND = "Unable to find {} {}.";
    private static final String PERSON_EXIST = "Person {} {} already exist.";

    private PersonService service;

    public PersonController() {
        loadPersonService();
    }

    private void loadPersonService() {
        final var tempService = ServiceLoader.load(PersonService.class);
        service = tempService.findFirst().orElseThrow(() -> new IllegalStateException("Unable to find PersonService."));
    }

    @GetMapping("communityEmail")
    public ResponseEntity<List<String>> getCommunityEmail(@RequestParam("city") final String city) {
        final var communityEmail = service.getCommunityEmail(city);
        return new ResponseEntity<>(communityEmail, HttpStatus.OK);
    }

    @DeleteMapping("/person")
    public ResponseEntity<Person> deletePerson(@RequestBody final Person person) {
        try {
            final var deletedPerson = service.deletePerson(person);
            return new ResponseEntity<>(deletedPerson, HttpStatus.OK);
        } catch (final UnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/person")
    public ResponseEntity<Person> updatePerson(@RequestBody final Person person) {
        try {
            final var updatedPerson = service.updatePerson(person);
            return new ResponseEntity<>(updatedPerson, HttpStatus.OK);
        } catch (final UnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/person")
    public ResponseEntity<Person> createPerson(@RequestBody final Person person) {
        try {
            final var createdPerson = service.createPerson(person);
            return new ResponseEntity<>(createdPerson, HttpStatus.CREATED);
        } catch (final PersonAlreadyExist ex) {
            LOGGER.error(PERSON_EXIST, ex.getFirstName(), ex.getLastName());
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
}