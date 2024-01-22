package fr.swynn.core;

import java.io.Serial;

public class UnknownPerson extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;

    public UnknownPerson(final String firstName, final String lastName) {
        super("Unknown person: " + firstName + " " + lastName);
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