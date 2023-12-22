package fr.swynn.launcher;

import fr.swynn.gateway.core.Gateway;

public class ConfiguredGatewayProxy extends GatewayProxy {

    static {
        new SafetyNetApplication();
    }

    public static Gateway gateway() {
        return GatewayProxy.initializeGateway();
    }

    public static void resetSingleton() {
        GatewayProxy.gateway = null;
    }
}
