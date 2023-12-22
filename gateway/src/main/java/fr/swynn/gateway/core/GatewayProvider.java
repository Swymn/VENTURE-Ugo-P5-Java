package fr.swynn.gateway.core;

public class GatewayProvider implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return SafetyNetGateway.getInstance();
    }
}
