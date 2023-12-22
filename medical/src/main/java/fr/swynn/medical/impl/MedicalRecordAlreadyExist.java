package fr.swynn.medical.impl;

import java.io.Serial;

public class MedicalRecordAlreadyExist extends Exception {

    @Serial
    private static final long serialVersionUID = 1L;

    private final String firstName;
    private final String lastName;

    public MedicalRecordAlreadyExist(final String firstName, final String lastName) {
        super("Medical record already exist for " + firstName + " " + lastName);
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
