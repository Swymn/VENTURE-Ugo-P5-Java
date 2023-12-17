package fr.swynn.gateway.core;

public record GatewayPerson(String firstName, String lastName, String address, String city, String zip, String phone, String email) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GatewayPerson person)) {
            return false;
        }

        return firstName.equals(person.firstName) && lastName.equals(person.lastName);
    }
}
