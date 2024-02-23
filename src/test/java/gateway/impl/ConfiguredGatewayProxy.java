package gateway.impl;

import fr.swynn.gateway.Gateway;
import fr.swynn.gateway.GatewayProxy;

public class ConfiguredGatewayProxy implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return new FakeGateway();
    }
}
