package fr.swynn.gateway.impl;

import fr.swynn.gateway.Gateway;
import fr.swynn.gateway.GatewayProxy;

public class GatewayProvider implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return SafetyNetGateway.getInstance();
    }
}
