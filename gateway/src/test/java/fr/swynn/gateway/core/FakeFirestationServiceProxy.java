package fr.swynn.gateway.core;

import java.util.ArrayList;
import java.util.List;

public class FakeFirestationServiceProxy implements FirestationServiceProxy {

    private final List<GatewayFirestation> firestations;

    public FakeFirestationServiceProxy() {
        firestations = getFirestations();
    }

    private List<GatewayFirestation> getFirestations() {
        final List<GatewayFirestation> gatewayFirestations = new ArrayList<>();
        gatewayFirestations.add(new GatewayFirestation("1509 Culver St", "3"));
        gatewayFirestations.add(new GatewayFirestation("29 15th St", "2"));
        gatewayFirestations.add(new GatewayFirestation("834 Binoc Ave", "3"));
        gatewayFirestations.add(new GatewayFirestation("644 Gershwin Cir", "1"));
        gatewayFirestations.add(new GatewayFirestation("748 Townings Dr", "3"));
        gatewayFirestations.add(new GatewayFirestation("112 Steppes Pl", "3"));

        return gatewayFirestations;
    }

    @Override
    public GatewayFirestation createFirestation(final GatewayFirestation firestation) throws GatewayFirestationAlreadyExist {
        for (final var firestationInList : firestations) {
            if (firestationInList.address().equals(firestation.address())) {
                throw new GatewayFirestationAlreadyExist(firestation.address());
            }
        }

        firestations.add(firestation);
        return firestation;
    }
}
