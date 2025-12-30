package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class RandomAccessMemory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String manufacturer;

    @Column(nullable = false)
    private String type;            // DDR3, DDR4, DDR5...

    @Column(nullable = false)
    private String moduleType;      // DIMM/SO-DIMM

    @Column(nullable = false)
    @Positive(message = "Должен быть > 0")
    private float capacity;

    @Positive(message = "Должен быть > 0")
    private float frequencyMhz;

    @Positive(message = "Должен быть > 0")
    private float casLatency;

    private boolean supportsEcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public float getFrequencyMhz() {
        return frequencyMhz;
    }

    public void setFrequencyMhz(float frequencyMhz) {
        this.frequencyMhz = frequencyMhz;
    }

    public float getCasLatency() {
        return casLatency;
    }

    public void setCasLatency(float casLatency) {
        this.casLatency = casLatency;
    }

    public boolean isSupportingEcc() {
        return supportsEcc;
    }

    public void setSupportsEcc(boolean supportsEcc) {
        this.supportsEcc = supportsEcc;
    }
}
