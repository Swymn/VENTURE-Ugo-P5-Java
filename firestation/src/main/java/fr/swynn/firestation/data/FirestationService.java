package fr.swynn.firestation.data;

import fr.swynn.firestation.model.Firestation;
import fr.swynn.firestation.impl.FirestationAlreadyExist;
import fr.swynn.firestation.impl.UnknownFirestation;

public interface FirestationService {

    Firestation deleteInferno(Firestation firestation) throws UnknownFirestation;

    Firestation updateInferno(Firestation firestation) throws UnknownFirestation;

    Firestation createInferno(Firestation firestation) throws FirestationAlreadyExist;

}
