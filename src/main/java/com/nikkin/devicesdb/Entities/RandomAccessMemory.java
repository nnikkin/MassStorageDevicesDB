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
    private String name;

    @Size(max=30)
    private String model;

    @Size(max=30)
    private String manufacturer;

    @Size(max=10)
    private String memoryType;            // DDR3, DDR4, DDR5...

    @Size(max=10)
    private String moduleType;      // DIMM/SO-DIMM

    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;

    @Nullable
    @Positive(message = "Тактовая частота не может быть отрицательным либо равным нулю")
    private Float frequencyMhz;

    @Nullable
    @Positive(message = "CAS-латентность не может быть отрицательной либо равной нулю")
    private Integer casLatency;

    @NotNull
    private Boolean supportsEcc;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getMemoryType() {
        return memoryType;
    }

    public void setMemoryType(String memoryType) {
        this.memoryType = memoryType;
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

    @Nullable
    public Float getFrequencyMhz() {
        return frequencyMhz;
    }

    public void setFrequencyMhz(@Nullable Float frequencyMhz) {
        this.frequencyMhz = frequencyMhz;
    }

    @Nullable
    public Integer getCasLatency() {
        return casLatency;
    }

    public void setCasLatency(@Nullable Integer casLatency) {
        this.casLatency = casLatency;
    }

    public boolean isSupportingEcc() {
        return supportsEcc;
    }

    public void setSupportsEcc(boolean supportsEcc) {
        this.supportsEcc = supportsEcc;
    }
}
