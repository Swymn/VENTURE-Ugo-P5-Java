package fr.swynn.launcher;

import fr.swynn.firestation.data.FirestationService;
import fr.swynn.firestation.impl.FirestationAlreadyExist;
import fr.swynn.firestation.impl.UnknownFirestation;
import fr.swynn.gateway.core.FirestationServiceProxy;
import fr.swynn.gateway.core.GatewayFirestation;
import fr.swynn.gateway.core.GatewayFirestationAlreadyExist;
import fr.swynn.gateway.core.GatewayUnknownFirestation;

import java.util.List;
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
    public List<String> getFirestationAddressByStationNumber(String station) throws GatewayUnknownFirestation {
        try {
            return service.getFirestationAddressByStationNumber(station);
        } catch (final UnknownFirestation ex) {
            throw new GatewayUnknownFirestation(ex.getAddress());
        }
    }

    @Override
    public GatewayFirestation createFirestation(final GatewayFirestation gatewayFirestation) throws GatewayFirestationAlreadyExist {
        try {
            final var firestation = mapper.map(gatewayFirestation);
            final var createdFirestation = service.createFirestation(firestation);
            return mapper.map(createdFirestation);
        } catch (final FirestationAlreadyExist ex) {
            throw new GatewayFirestationAlreadyExist(ex.getAddress());
        }
    }

    @Override
    public GatewayFirestation updateFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation {
        try {
            final var firestationToUpdate = mapper.map(firestation);
            final var updatedFirestation = service.updateFirestation(firestationToUpdate);
            return mapper.map(updatedFirestation);
        } catch (final UnknownFirestation ex) {
            throw new GatewayUnknownFirestation(ex.getAddress());
        }
    }

    @Override
    public GatewayFirestation deleteFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation {
        try {
            final var firestationToDelete = mapper.map(firestation);
            final var deletedFirestation = service.deleteFirestation(firestationToDelete);
            return mapper.map(deletedFirestation);
        } catch (final UnknownFirestation ex) {
            throw new GatewayUnknownFirestation(ex.getAddress());
        }
    }
}
