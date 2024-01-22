package core;

import fr.swynn.core.FirestationAlreadyExist;
import fr.swynn.core.FirestationService;
import fr.swynn.core.UnknownFirestation;
import fr.swynn.model.Firestation;

import java.util.ArrayList;
import java.util.List;

public class FakeFirestationService implements FirestationService {

    private final List<Firestation> firestations;

    public FakeFirestationService() {
        this.firestations = getFirestations();
    }

    private List<Firestation> getFirestations() {
        final List<Firestation> gatewayFirestations = new ArrayList<>();
        gatewayFirestations.add(new Firestation("1509 Culver St", "3"));
        gatewayFirestations.add(new Firestation("29 15th St", "2"));
        gatewayFirestations.add(new Firestation("834 Binoc Ave", "3"));
        gatewayFirestations.add(new Firestation("644 Gershwin Cir", "1"));
        gatewayFirestations.add(new Firestation("748 Townings Dr", "3"));
        gatewayFirestations.add(new Firestation("112 Steppes Pl", "3"));

        return gatewayFirestations;
    }

    @Override
    public List<String> getFirestationAddressByStationNumber(String station) throws UnknownFirestation {
        final var stationsAddress =
                firestations.stream().filter(firestation -> firestation.station().equals(station)).map(Firestation::address).toList();
        if (stationsAddress.isEmpty()) {
            throw new UnknownFirestation(station);
        }

        return stationsAddress;
    }

    @Override
    public Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address())) {
                firestations.remove(i);
                return firestation;
            }
        }

        throw new UnknownFirestation(firestation.address());

    }

    @Override
    public Firestation updateFirestation(Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address())) {
                firestations.set(i, firestation);
                return firestation;
            }
        }

        throw new UnknownFirestation(firestation.address());
    }

    @Override
    public Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist {
        for (final var firestationInList : firestations) {
            if (firestationInList.address().equals(firestation.address())) {
                throw new FirestationAlreadyExist(firestation.address());
            }
        }

        firestations.add(firestation);
        return firestation;
    }
}
