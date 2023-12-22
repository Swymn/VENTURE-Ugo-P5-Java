package fr.swynn.firestation.impl;

import java.io.Serial;

public class FirestationAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String address;

    public FirestationAlreadyExist(final String address) {
        super(String.format("Firestation located at %s already exist", address));
        this.address = address;
    }

    public String getAddress() {
        return address;
    }
}
