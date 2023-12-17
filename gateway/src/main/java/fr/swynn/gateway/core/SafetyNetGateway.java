package fr.swynn.gateway.core;

import java.util.ServiceLoader;

public class SafetyNetGateway implements Gateway {

    private PersonServiceProxy personProxy;

    public SafetyNetGateway() {
        loadPersonProxy();
    }

    private void loadPersonProxy() {
        final var proxy = ServiceLoader.load(PersonServiceProxy.class);
        personProxy = proxy.findFirst().orElseThrow();
    }

    @Override
    public GatewayPerson getPerson(String firstName, String lastName) throws GatewayUnknownPerson {
        return personProxy.getPersona(firstName, lastName);
    }
}
