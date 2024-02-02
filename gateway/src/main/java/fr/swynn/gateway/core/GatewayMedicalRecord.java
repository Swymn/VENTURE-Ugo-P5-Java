package fr.swynn.gateway.core;

public record GatewayMedicalRecord(String firstName, String lastName, String age, String[] medications, String[] allergies) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GatewayMedicalRecord medical)) {
            return false;
        }

        return medical.firstName().equals(firstName()) && medical.lastName().equals(lastName());
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age='" + age + '\'' +
                ", medications=" + String.join(", ", medications) +
                ", allergies=" + String.join(", ", allergies) +
                '}';
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode();
    }
}