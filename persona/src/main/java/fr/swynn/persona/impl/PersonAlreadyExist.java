package fr.swynn.persona.impl;

import java.io.Serial;

public class PersonAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;

    public PersonAlreadyExist(final String firstName, final String lastName) {
        super(String.format("Person %s %s already exist", firstName, lastName));
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
