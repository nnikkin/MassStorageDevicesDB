package com.nikkin.devicesdb.Domain;

public enum UsbInterface {
    TYPE_A("Type-A"), TYPE_C("Type-C"), MICRO("Micro-USB");
    private final String label;

    UsbInterface(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static UsbInterface valueOfLabel(String label) {
        for (UsbInterface b : values()) {
            if (java.util.Objects.equals(b.label, label)) {
                return b;
            }
        }
        return null;
    }
}
