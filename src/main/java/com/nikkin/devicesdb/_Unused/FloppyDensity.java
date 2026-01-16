package com.nikkin.devicesdb._Unused;

public enum FloppyDensity {
    SD("SD (Single Density)"), DD("DD (Double Density)"), QD("QD (Quad Density)"),
    HD("HD (High Density)"), ED("ED (Extra-High Density)");

    private final String label;

    FloppyDensity(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static FloppyDensity valueOfLabel(String label) {
        for (FloppyDensity b : values()) {
            if (java.util.Objects.equals(b.label, label)) {
                return b;
            }
        }
        return null;
    }
}
