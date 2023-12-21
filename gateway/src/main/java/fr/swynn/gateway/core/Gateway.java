package fr.swynn.gateway.core;

public interface Gateway {

    GatewayPersona deletePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona updatePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona createPerson(GatewayPersona person) throws GatewayPersonAlreadyExist;

    GatewayFirestation createFirestation(GatewayFirestation firestation) throws GatewayFirestationAlreadyExist;
}
