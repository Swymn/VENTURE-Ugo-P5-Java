package fr.swynn.dto;

import java.util.List;

public record ChildCitizen(String firstName, String lastName, List<Citizen> familyMembers) {
}
