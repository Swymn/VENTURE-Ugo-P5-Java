package fr.swynn.gateway.core;

import java.io.Serial;

public class GatewayUnknownPerson extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public GatewayUnknownPerson(final String firstName, final String lastName) {
        super("Unknown person: " + firstName + " " + lastName);
    }
}
