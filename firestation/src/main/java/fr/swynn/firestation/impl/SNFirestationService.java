package fr.swynn.firestation.impl;

import fr.swynn.firestation.data.FirestationRepository;
import fr.swynn.firestation.data.FirestationService;
import fr.swynn.firestation.model.Firestation;

import java.util.List;
import java.util.ServiceLoader;

public class SNFirestationService implements FirestationService {

    private FirestationRepository repository;

    private final List<Firestation> firestations;

    public SNFirestationService() {
        loadFirestationRepository();

        firestations = repository.getAllfirestations();
    }

    private void loadFirestationRepository() {
        final var firestationRepo = ServiceLoader.load(FirestationRepository.class);
        repository = firestationRepo.findFirst().orElseThrow();
    }

    @Override
    public Firestation deleteInferno(final Firestation firestation) throws UnknownFirestation {
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
    public Firestation updateInferno(final Firestation firestation) throws UnknownFirestation {
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
    public Firestation createInferno(final Firestation firestation) throws FirestationAlreadyExist {
        for (final var firestationInList : firestations) {
            if (firestation.equals(firestationInList)) {
                throw new FirestationAlreadyExist(firestation.address());
            }
        }
        firestations.add(firestation);
        return firestation;
    }
}
