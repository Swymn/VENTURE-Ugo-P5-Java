package core;

import fr.swynn.core.FirestationRepository;
import fr.swynn.model.Firestation;

import java.util.ArrayList;
import java.util.List;

public class FakeFirestationRepository implements FirestationRepository {

    @Override
    public List<Firestation> getAllfirestations() {
        final List<Firestation> firestations = new ArrayList<>();

        firestations.add(new Firestation("1509 Culver St", "3"));
        firestations.add(new Firestation("29 15th St", "2"));
        firestations.add(new Firestation("834 Binoc Ave", "3"));
        firestations.add(new Firestation("644 Gershwin Cir", "1"));
        firestations.add(new Firestation("748 Townings Dr", "3"));
        firestations.add(new Firestation("112 Steppes Pl", "3"));

        return firestations;
    }
}
