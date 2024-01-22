package fr.swynn.core;

import fr.swynn.model.Firestation;

import java.util.List;

public interface FirestationService {
    List<String> getFirestationAddressByStationNumber(String station) throws UnknownFirestation;

    Firestation deleteFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation updateFirestation(Firestation firestation) throws UnknownFirestation;

    Firestation createFirestation(Firestation firestation) throws FirestationAlreadyExist;
}
