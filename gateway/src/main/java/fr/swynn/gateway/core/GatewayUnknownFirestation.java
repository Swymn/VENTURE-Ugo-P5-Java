package fr.swynn.gateway.core;

import java.io.Serial;

public class GatewayUnknownFirestation extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String address;

    public GatewayUnknownFirestation(final String address) {
        super("Unknown firestation with address: " + address);
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
