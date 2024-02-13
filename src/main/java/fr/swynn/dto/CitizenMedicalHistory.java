package fr.swynn.dto;

public record CitizenMedicalHistory(String firstName, String lastName, String phoneNumber, int age, String[] medications, String[] allergies) {

}
