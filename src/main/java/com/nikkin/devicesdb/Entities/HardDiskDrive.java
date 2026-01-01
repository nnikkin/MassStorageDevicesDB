package com.nikkin.devicesdb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class HardDiskDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=30)
    private String name;

    @NotBlank
    @Size(min=1,max=10)
    private String driveInterface;       // SATA...

    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;

    @Nullable
    @Positive(message = "Формат не может быть отрицательным либо равным нулю")
    private Float format;           // 3.5", 2.5"

    @Nullable
    @Positive(message = "RPM не может быть отрицательным либо равным нулю")
    private Integer rpm;                // 5400, 7200...

    @Nullable
    @Positive(message = "Объём кэша не может быть отрицательным либо равным нулю")
    private Integer cache;

    @Nullable
    @Positive(message = "Энергопотребление не может быть отрицательным либо равным нулю")
    private Float powerConsumption;

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

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    @Nullable
    public Float getFormat() {
        return format;
    }

    public void setFormat(@Nullable Float format) {
        this.format = format;
    }

    @Nullable
    public Integer getRpm() {
        return rpm;
    }

    public void setRpm(@Nullable Integer rpm) {
        this.rpm = rpm;
    }

    @Nullable
    public Integer getCache() {
        return cache;
    }

    public void setCache(@Nullable Integer cache) {
        this.cache = cache;
    }

    @Nullable
    public Float getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(@Nullable Float powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
}
