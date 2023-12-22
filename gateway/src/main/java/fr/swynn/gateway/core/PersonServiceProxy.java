package fr.swynn.gateway.core;

import java.util.List;

public interface PersonServiceProxy {

    List<GatewayPersona> getPersonByAddress(String address);

    GatewayPersona deletePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona updatePerson(GatewayPersona person) throws GatewayUnknownPerson;

    GatewayPersona createPerson(GatewayPersona person) throws GatewayPersonAlreadyExist;
}
