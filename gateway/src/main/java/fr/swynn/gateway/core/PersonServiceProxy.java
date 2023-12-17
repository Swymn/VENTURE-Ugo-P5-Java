package fr.swynn.gateway.core;

public interface PersonServiceProxy {
    GatewayPerson getPersona(String firstName, String lastName) throws GatewayUnknownPerson;
}
