package fr.swynn.core;

import java.io.Serial;

public class UnknownMedicalRecord extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;

    public UnknownMedicalRecord(final String firstName, final String lastName) {
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
