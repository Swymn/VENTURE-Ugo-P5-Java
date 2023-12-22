package fr.swynn.gateway.core;

import java.io.Serial;

public class GatewayMedicalRecordAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;

    public GatewayMedicalRecordAlreadyExist(String firstName, String lastName) {
        super("Medical record already existed for " + firstName + " " + lastName);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
