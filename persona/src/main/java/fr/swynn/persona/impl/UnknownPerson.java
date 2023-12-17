package fr.swynn.persona.impl;

import java.io.Serial;

public class UnknownPerson extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public UnknownPerson(final String firstName, final String lastName) {
        super("Unknown person: " + firstName + " " + lastName);
    }
}
