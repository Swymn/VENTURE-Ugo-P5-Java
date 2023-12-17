package fr.swynn.persona.model;

public record Persona(String firstName, String lastName, String address, String city, String zip, String phone, String email) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Persona person)) {
            return false;
        }

        return firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }
}