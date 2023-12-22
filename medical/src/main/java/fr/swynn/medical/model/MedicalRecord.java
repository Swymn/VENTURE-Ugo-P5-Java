package fr.swynn.medical.model;

public record MedicalRecord(String firstName, String lastName, String birthdate, String[] medications, String[] allergies) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MedicalRecord medical)) {
            return false;
        }

        return medical.firstName().equals(firstName()) && medical.lastName().equals(lastName());
    }

    @Override
    public String toString() {
        return "MedicalRecord{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", medications=" + String.join(", ", medications) +
                ", allergies=" + String.join(", ", allergies) +
                '}';
    }

    @Override
    public int hashCode() {
        return firstName.hashCode() + lastName.hashCode();
    }
}
