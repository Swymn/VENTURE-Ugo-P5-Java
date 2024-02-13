package fr.swynn.core;

import fr.swynn.model.Firestation;
import fr.swynn.service.FirestationAlreadyExist;
import fr.swynn.service.UnknownFirestation;

import java.util.List;

public interface FirestationService {
    List<String> getFirestationAddressByStationNumber(String station) throws UnknownFirestation;

    Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation updateFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist;

    String getFirestationNumberByAddress(String address) throws UnknownFirestation;
}
