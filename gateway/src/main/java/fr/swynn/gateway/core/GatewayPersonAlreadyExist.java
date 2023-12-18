package fr.swynn.gateway.core;

import java.io.Serial;

public class GatewayPersonAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public final String firstName;
    public final String lastName;

    public GatewayPersonAlreadyExist(final String firstName, final String lastName) {
        super("Person already exist: " + firstName + " " + lastName);
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
