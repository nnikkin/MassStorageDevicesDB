package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;

@Entity
public class RandomAccessMemory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String model;

    private String manufacturer;

    private String memoryType;

    private String moduleType;

    private Float capacity;

    private Float frequencyMhz;

    private Integer casLatency;

    @ManyToOne
    @JoinColumn(name = "computer_id") // Имя колонки во внешнем ключе в БД
    private Computer computer;

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

    public Float getFrequencyMhz() {
        return frequencyMhz;
    }

    public void setFrequencyMhz(Float frequencyMhz) {
        this.frequencyMhz = frequencyMhz;
    }

    public Integer getCasLatency() {
        return casLatency;
    }

    public void setCasLatency(Integer casLatency) {
        this.casLatency = casLatency;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}
