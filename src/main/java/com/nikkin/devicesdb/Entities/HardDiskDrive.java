package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class HardDiskDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String driveInterface;       // SATA...

    @Column(nullable = false)
    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private float capacity;

    @Column(nullable = false)
    @Positive(message = "Формат не может быть отрицательным либо равным нулю")
    private float format;           // 3.5", 2.5"

    @Positive(message = "RPM не может быть отрицательным либо равным нулю")
    private int rpm;                // 5400, 7200...

    @Positive(message = "Объём кэша не может быть отрицательным либо равным нулю")
    private int cache;

    @Positive(message = "Энергопотребление не может быть отрицательным либо равным нулю")
    private float powerConsumption;

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

    public String getDriveInterface() {
        return driveInterface;
    }

    public void setDriveInterface(String driveInterface) {
        this.driveInterface = driveInterface;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public float getFormat() {
        return format;
    }

    public void setFormat(float format) {
        this.format = format;
    }

    public int getRpm() {
        return rpm;
    }

    public void setRpm(int rpm) {
        this.rpm = rpm;
    }

    public int getCache() {
        return cache;
    }

    public void setCache(int cache) {
        this.cache = cache;
    }

    public float getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(float powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
}
