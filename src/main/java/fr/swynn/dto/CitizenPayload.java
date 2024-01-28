package fr.swynn.dto;

import java.util.List;

public record CitizenPayload(List<Citizen> citizens, int adultCount, int childCount) {
}
