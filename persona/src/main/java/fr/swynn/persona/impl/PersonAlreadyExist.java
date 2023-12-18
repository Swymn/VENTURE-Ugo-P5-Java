package fr.swynn.persona.impl;

import java.io.Serial;

public class PersonAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    public PersonAlreadyExist(final String firstName, final String lastName) {
        super(String.format("Person %s %s already exist", firstName, lastName));
    }
}
