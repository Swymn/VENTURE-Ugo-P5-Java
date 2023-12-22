package fr.swynn.gateway.core;

public class ConfiguredGatewayProxy implements GatewayProxy {

    @Override
    public Gateway getGateway() {
        return new FakeGateway();
    }
}
