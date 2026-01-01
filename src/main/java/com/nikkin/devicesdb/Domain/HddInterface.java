package com.nikkin.devicesdb.Domain;

public enum HddInterface {
    SATA("SATA"), SCSI("SCSI"), SAS("SAS (Serial attached SCSI)"), IDE("IDE"),
    ESDI("ESDI");

    private final String label;

    HddInterface(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static HddInterface valueOfLabel(String label) {
        for (HddInterface b : values()) {
            if (java.util.Objects.equals(b.label, label)) {
                return b;
            }
        }
        return null;
    }
}
