package fr.swynn.gateway.core;

import java.util.List;

public interface FirestationServiceProxy {

    List<String> getFirestationAddressByStationNumber(String station) throws GatewayUnknownFirestation;

    GatewayFirestation createFirestation(GatewayFirestation firestation) throws GatewayFirestationAlreadyExist;

    GatewayFirestation updateFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation;

    GatewayFirestation deleteFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation;
}
