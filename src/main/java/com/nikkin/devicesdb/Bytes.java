package com.nikkin.devicesdb;

public enum Bytes {
    BIT("бит", 0, 1L),
    BYTE("байт", 1, 8L),
    KiB("килобайт", 2, 8L * 1024),
    MiB("мегабайт", 3, 8L * 1024 * 1024),
    GiB("гигабайт", 4, 8L * 1024 * 1024 * 1024),
    TiB("терабайт", 5, 8L * 1024 * 1024 * 1024 * 1024),
    PiB("петабайт", 6, 8L * 1024 * 1024 * 1024 * 1024 * 1024);

    private final String label;
    private final int rank;
    private final long bits;

    Bytes(String label, int rank, long bits) {
        this.label = label;
        this.rank = rank;
        this.bits = bits;
    }

    public String getLabel() { return label; }

    public int getRank() { return rank; }

    public long getBits() { return bits; }

    public static Bytes valueOfLabel(String label) {
        for (Bytes b : values()) {
            if (java.util.Objects.equals(b.label, label)) {
                return b;
            }
        }
        return null;
    }
}
