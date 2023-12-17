package fr.swynn.gateway.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ServiceLoader;

public class SafetyNetGateway implements Gateway {

    private static final Logger LOGGER;
    private static final String GATEWAY_LOADED_WITH_PROXY = "Gateway loaded with proxy: {}";

    private PersonServiceProxy personProxy;

    static {
        LOGGER = LoggerFactory.getLogger(SafetyNetGateway.class);
    }

    public SafetyNetGateway() {
        loadPersonProxy();
    }

    private void loadPersonProxy() {
        final var proxy = ServiceLoader.load(PersonServiceProxy.class);
        personProxy = proxy.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, personProxy.getClass().getName());
    }

    @Override
    public GatewayPerson getPerson(String firstName, String lastName) throws GatewayUnknownPerson {
        return personProxy.getPersona(firstName, lastName);
    }
}
