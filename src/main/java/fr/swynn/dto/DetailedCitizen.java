package fr.swynn.dto;

public record DetailedCitizen(String firstName, String lastName, String address, String email, String[] medications, String[] allergies) {
}
