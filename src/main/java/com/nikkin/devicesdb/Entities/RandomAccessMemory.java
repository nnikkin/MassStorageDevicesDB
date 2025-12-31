package com.nikkin.devicesdb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class RandomAccessMemory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=30)
    private String model;

    @Size(max=30)
    private String manufacturer;

    @Size(max=10)
    private String type;            // DDR3, DDR4, DDR5...

    @Size(max=10)
    private String moduleType;      // DIMM/SO-DIMM

    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;

    @Nullable
    @Positive(message = "Частота не может быть отрицательным либо равным нулю")
    private Float frequencyMhz;

    @Nullable
    @Positive(message = "Задержка не может быть отрицательным либо равным нулю")
    private Float casLatency;

    @NotNull
    private Boolean supportsEcc;

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

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Float getFrequencyMhz() {
        return frequencyMhz;
    }

    public void setFrequencyMhz(Float frequencyMhz) {
        this.frequencyMhz = frequencyMhz;
    }

    public Float getCasLatency() {
        return casLatency;
    }

    public void setCasLatency(Float casLatency) {
        this.casLatency = casLatency;
    }

    public boolean isSupportingEcc() {
        return supportsEcc;
    }

    public void setSupportsEcc(boolean supportsEcc) {
        this.supportsEcc = supportsEcc;
    }
}
