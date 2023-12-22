package fr.swynn.gateway.core;

public interface FirestationServiceProxy {

    GatewayFirestation createFirestation(GatewayFirestation firestation) throws GatewayFirestationAlreadyExist;

    GatewayFirestation updateFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation;

    GatewayFirestation deleteFirestation(GatewayFirestation firestation) throws GatewayUnknownFirestation;
}
