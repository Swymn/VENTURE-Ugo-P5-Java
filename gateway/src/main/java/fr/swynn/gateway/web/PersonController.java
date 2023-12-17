package fr.swynn.gateway.web;

import fr.swynn.gateway.core.Gateway;
import fr.swynn.gateway.core.GatewayPerson;
import fr.swynn.gateway.core.GatewayUnknownPerson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ServiceLoader;

@RestController
public class PersonController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonController.class);
    private static final String PERSON_NOT_FOUND = "Unable to find {} {}";

    private Gateway gateway;

    public PersonController() {
        loadGateway();
    }

    private void loadGateway() {
        final var loadedGateway = ServiceLoader.load(Gateway.class);
        gateway = loadedGateway.findFirst().orElseThrow();
    }

    @GetMapping("/person")
    public GatewayPerson getPerson(@RequestParam("firstName") final String firstName, @RequestParam("lastName") final String lastName) {
        try {
            return gateway.getPerson(firstName, lastName);
        } catch (final GatewayUnknownPerson ex) {
            LOGGER.error(PERSON_NOT_FOUND, firstName, lastName);
            return null;
        }
    }
}
