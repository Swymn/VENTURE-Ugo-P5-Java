package fr.swynn.service;

import java.io.Serial;

public class UnknownFirestation extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String address;

    public UnknownFirestation(final String address) {
        super("Unknown person: " + address + " ");
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
