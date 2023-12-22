package fr.swynn.launcher;

import fr.swynn.firestation.data.FirestationService;
import fr.swynn.firestation.impl.FirestationAlreadyExist;
import fr.swynn.gateway.core.FirestationServiceProxy;
import fr.swynn.gateway.core.GatewayFirestation;
import fr.swynn.gateway.core.GatewayFirestationAlreadyExist;

import java.util.ServiceLoader;

public class GatewayToFirestationService implements FirestationServiceProxy {

    private final GatewayObjectMapper mapper;
    private FirestationService service;

    public GatewayToFirestationService() {
        mapper = new GatewayObjectMapper();
        loadFirestationService();
    }

    private void loadFirestationService() {
        final var loadedService = ServiceLoader.load(FirestationService.class);
        service = loadedService.findFirst().orElseThrow();
    }

    @Override
    public GatewayFirestation createFirestation(final GatewayFirestation gatewayFirestation) throws GatewayFirestationAlreadyExist {
        try {
            final var firestation = mapper.map(gatewayFirestation);
            final var createdFirestation = service.createInferno(firestation);
            return mapper.map(createdFirestation);
        } catch (final FirestationAlreadyExist ex) {
            throw new GatewayFirestationAlreadyExist(ex.getAddress());
        }
    }
}
