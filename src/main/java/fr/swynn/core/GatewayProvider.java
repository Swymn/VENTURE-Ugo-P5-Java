package fr.swynn.core;

public class GatewayProvider implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return SafetyNetGateway.getInstance();
    }
}
