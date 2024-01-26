package core;

import fr.swynn.core.Gateway;
import fr.swynn.core.GatewayProxy;

public class ConfiguredGatewayProxy implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return new FakeGateway();
    }
}
