package com.nikkin.devicesdb.Domain;

public enum SsdInterface {
    SATA("SATA"), PCIE("PCI Express"), SAS("SAS (Serial attached SCSI)"),
    M2("M.2"), NVME("NVMe (NVM Express)"), AHCI("AHCI (Advanced Host Controller Interface)");

    private final String label;

    SsdInterface(String label) { this.label = label; }

    public String getLabel() { return label; }

    public static SsdInterface valueOfLabel(String label) {
        for (SsdInterface b : values()) {
            if (java.util.Objects.equals(b.label, label)) {
                return b;
            }
        }
        return null;
    }
}
