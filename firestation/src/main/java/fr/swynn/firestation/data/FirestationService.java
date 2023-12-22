package fr.swynn.firestation.data;

import fr.swynn.firestation.model.Firestation;
import fr.swynn.firestation.impl.FirestationAlreadyExist;
import fr.swynn.firestation.impl.UnknownFirestation;

public interface FirestationService {

    Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation updateFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist;

}
