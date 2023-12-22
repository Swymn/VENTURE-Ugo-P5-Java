package fr.swynn.firestation.model;

public record Firestation(String address, String station) {

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Firestation firestation)) {
            return false;
        }

        return address.equals(firestation.address);
    }
}
