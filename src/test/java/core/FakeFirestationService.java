package core;

import fr.swynn.core.FirestationService;
import fr.swynn.model.Firestation;
import fr.swynn.service.FirestationAlreadyExist;
import fr.swynn.service.UnknownFirestation;

import java.util.ArrayList;
import java.util.List;

public class FakeFirestationService implements FirestationService {

    private static List<Firestation> firestations;

    public FakeFirestationService() {
        initFirestations();
    }

    public static void initFirestations() {
        firestations = getInfernos();
    }

    private static List<Firestation> getInfernos() {
        final List<Firestation> firestations = new ArrayList<>();

        firestations.add(new Firestation("1509 Baylee St", "1"));
        firestations.add(new Firestation("2 rue de la paix", "2"));
        firestations.add(new Firestation("3 rue de la paix", "3"));
        firestations.add(new Firestation("1509 Culver St", "1"));
        firestations.add(new Firestation("5 rue de la paix", "2"));
        firestations.add(new Firestation("6 rue de la paix", "3"));
        firestations.add(new Firestation("7 rue de la paix", "2"));
        firestations.add(new Firestation("8 rue de la paix", "1"));

        return firestations;
    }

    @Override
    public List<String> getFirestationAddressByStationNumber(String station) throws UnknownFirestation {
        final var addresses = new ArrayList<String>();
        for (final var firestation : firestations) {
            if (firestation.station().equals(station)) {
                addresses.add(firestation.address());
            }
        }

        if (addresses.isEmpty()) {
            throw new UnknownFirestation(station);
        }

        return addresses;
    }

    @Override
    public Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation {
        for (int i = 0; i < firestations.size(); i++) {
            final var firestationInList = firestations.get(i);
            if (firestationInList.address().equals(firestation.address()) && firestationInList.station().equals(firestation.station())) {
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
            if (firestationInList.address().equals(firestation.address()) && firestationInList.station().equals(firestation.station())) {
                firestations.set(i, new Firestation(firestation.address(), firestation.station()));
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

    @Override
    public String getFirestationNumberByAddress(String address) throws UnknownFirestation {
        return firestations.stream()
                .filter(firestation -> firestation.address().equals(address))
                .findFirst()
                .map(Firestation::station)
                .orElseThrow(() -> new UnknownFirestation(address));
    }
}
