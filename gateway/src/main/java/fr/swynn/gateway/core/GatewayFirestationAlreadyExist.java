package fr.swynn.gateway.core;

import java.io.Serial;

public class GatewayFirestationAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public GatewayFirestationAlreadyExist(final String address) {
        super("Firestation already exist for address: " + address + ".");
    }
}
