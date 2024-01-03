package fr.swynn.firestation.impl;

import fr.swynn.firestation.data.FirestationRepository;
import fr.swynn.firestation.data.FirestationService;
import fr.swynn.firestation.model.Firestation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

public class SNFirestationService implements FirestationService {

    private static final Logger LOGGER;
    private static final String SERVICE_LOADED;

    private FirestationRepository repository;

    private final List<Firestation> firestations;

    static {
        LOGGER = LoggerFactory.getLogger(SNFirestationService.class);
        SERVICE_LOADED = "Firestation service is loaded.";
    }

    public SNFirestationService() {
        loadFirestationRepository();

        firestations = repository.getAllfirestations();
        LOGGER.info(SERVICE_LOADED);
    }

    private void loadFirestationRepository() {
        final var firestationRepo = ServiceLoader.load(FirestationRepository.class);
        repository = firestationRepo.findFirst().orElseThrow();
    }

    @Override
    public List<String> getFirestationAddressByStationNumber(String station) throws UnknownFirestation {
        final var addresses = firestations.stream()
                .filter(firestation -> firestation.station().equals(station))
                .map(Firestation::address)
                .toList();

        if (addresses.isEmpty()) {
            throw new UnknownFirestation(station);
        }

        return addresses;
    }

    @Override
    public Firestation deleteFirestation(final Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestation.equals(firestationInList)) {
                firestations.remove(i);
                return firestation;
            }
        }
        throw new UnknownFirestation(firestation.address());
    }

    @Override
    public Firestation updateFirestation(final Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestation.equals(firestationInList)) {
                firestations.set(i, firestation);
                return firestation;
            }
        }

        throw new UnknownFirestation(firestation.address());
    }

    @Override
    public Firestation createFirestation(final Firestation firestation) throws FirestationAlreadyExist {
        for (final var firestationInList : firestations) {
            if (firestation.equals(firestationInList)) {
                throw new FirestationAlreadyExist(firestation.address());
            }
        }
        firestations.add(firestation);
        return firestation;
    }
}
