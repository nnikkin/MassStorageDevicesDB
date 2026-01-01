package com.nikkin.devicesdb.Domain;

public enum Bytes {
    BIT("бит", 0), B("байт", 1), KB("килобайт", 2),
    MB("мегабайт", 3), GB("гигабайт", 4),
    TB("терабайт", 5), PB("петабайт", 6);

    private final String label;
    private final int rank;

    Bytes(String label, int rank) {
        this.label = label; this.rank = rank;
    }

    public String getLabel() { return label; }

    public int getRank() { return rank; }

    public static Bytes valueOfLabel(String label) {
        for (Bytes b : values()) {
            if (java.util.Objects.equals(b.label, label)) {
                return b;
            }
        }
        return null;
    }
}
