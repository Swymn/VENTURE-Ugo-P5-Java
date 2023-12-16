package fr.swynn.launcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "fr.swynn.gateway" })
public class SafetyNetApplication {
    public static void main(final String[] args) {
        SpringApplication.run(SafetyNetApplication.class, args);
    }
}