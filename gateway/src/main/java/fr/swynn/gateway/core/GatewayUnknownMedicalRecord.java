package fr.swynn.gateway.core;

import java.io.Serial;

public class GatewayUnknownMedicalRecord extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;

    public GatewayUnknownMedicalRecord(String firstName, String lastName) {
        super("Unknown medical record for " + firstName + " " + lastName);
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
