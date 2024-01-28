package fr.swynn.gateway;

import fr.swynn.core.Gateway;
import fr.swynn.core.GatewayProxy;

public class GatewayProvider implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return SafetyNetGateway.getInstance();
    }
}
