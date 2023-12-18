package fr.swynn.gateway.core;

public interface PersonServiceProxy {
    GatewayPerson getPerson(String firstName, String lastName) throws GatewayUnknownPerson;

    GatewayPerson updatePerson(GatewayPerson person) throws GatewayUnknownPerson;

    GatewayPerson createPerson(GatewayPerson person) throws GatewayPersonAlreadyExist;
}
