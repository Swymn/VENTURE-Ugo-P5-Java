package fr.swynn.firestation.data;

import fr.swynn.firestation.model.Firestation;
import fr.swynn.firestation.impl.FirestationAlreadyExist;
import fr.swynn.firestation.impl.UnknownFirestation;

import java.util.List;

public interface FirestationService {

    List<String> getFirestationAddressByStationNumber(String station) throws UnknownFirestation;

    Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation updateFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist;

}
