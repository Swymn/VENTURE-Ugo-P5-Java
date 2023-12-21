package fr.swynn.gateway.core;

public interface FirestationServiceProxy {

    GatewayFirestation createFirestation(GatewayFirestation firestation) throws GatewayFirestationAlreadyExist;
}
