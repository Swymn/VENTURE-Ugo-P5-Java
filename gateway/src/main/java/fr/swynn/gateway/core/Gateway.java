package fr.swynn.gateway.core;

public interface Gateway {

    GatewayPerson getPerson(String firstName, String lastName) throws GatewayUnknownPerson;

}
