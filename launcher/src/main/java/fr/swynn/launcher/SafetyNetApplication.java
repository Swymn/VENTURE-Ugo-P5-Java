package fr.swynn.launcher;

import fr.swynn.firestation.data.FirestationService;
import fr.swynn.firestation.impl.SNFirestationService;
import fr.swynn.gateway.core.Gateway;
import fr.swynn.gateway.core.SafetyNetGateway;
import fr.swynn.medical.data.MedicalService;
import fr.swynn.medical.impl.SNMedicalService;
import fr.swynn.persona.data.PersonaService;
import fr.swynn.persona.impl.SNPersonaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.ServiceLoader;

@SpringBootApplication(scanBasePackages = { "fr.swynn.gateway.web", "fr.swynn.launcher" })
public class SafetyNetApplication {

    private static final Logger LOGGER;
    private static final String INFO_RUNNING;
    private static final String GATEWAY_LOADED;
    private static final String GATEWAY_LOADED_WITH_PROXY;
    private static Gateway gateway;
    private static PersonaService personaService;
    private static FirestationService firestationService;
    private static MedicalService medicalService;

    static {
        LOGGER = LoggerFactory.getLogger(SafetyNetApplication.class);
        INFO_RUNNING = "SafetyNetApplication is running.";
        GATEWAY_LOADED = "Gateway is loaded.";
        GATEWAY_LOADED_WITH_PROXY = "Connection between Gateway and {} is established.";
    }

    protected SafetyNetApplication() {
        LOGGER.info(INFO_RUNNING);
        initAllServices();
    }

    private static void initAllServices() {
        personaService = initPersonaService();
        firestationService = initFirestationService();
        medicalService = initMedicalService();

        gateway = initGateway();
    }

    private static PersonaService initPersonaService() {
        final var loadedService = ServiceLoader.load(PersonaService.class);
        final var service = loadedService.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, "PersonaService");
        return service;
    }

    private static FirestationService initFirestationService() {
        final var loadedService = ServiceLoader.load(FirestationService.class);
        final var service = loadedService.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, "FirestationService");
        return service;
    }

    private static MedicalService initMedicalService() {
        final var loadedService = ServiceLoader.load(MedicalService.class);
        final var service = loadedService.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED_WITH_PROXY, "MedicalService");
        return service;
    }

    private static Gateway initGateway() {
        final var loadedGateway = ServiceLoader.load(Gateway.class);
        final var gateway = loadedGateway.findFirst().orElseThrow();
        LOGGER.info(GATEWAY_LOADED);
        return gateway;
    }

    public static PersonaService getPersonaService() {
        return personaService;
    }

    public static FirestationService getFirestationService() {
        return firestationService;
    }

    public static MedicalService getMedicalService() {
        return medicalService;
    }

    public static Gateway getGateway() {
        return gateway;
    }

    public static void main(final String[] args) {
        SpringApplication.run(SafetyNetApplication.class, args);
    }
}