package fr.swynn.gateway.web;

import fr.swynn.gateway.core.Gateway;
import fr.swynn.gateway.core.GatewayPerson;
import org.springframework.web.bind.annotation.*;

import java.util.ServiceLoader;

@RestController
public class PersonController {

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
        return gateway.getPerson(firstName, lastName);
    }
}
