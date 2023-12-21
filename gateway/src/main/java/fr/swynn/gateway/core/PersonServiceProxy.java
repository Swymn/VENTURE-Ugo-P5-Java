package fr.swynn.gateway.core;

public interface PersonServiceProxy {
    GatewayPersona deletePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona updatePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona createPerson(GatewayPersona person) throws GatewayPersonAlreadyExist;
}
