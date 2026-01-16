package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;

@Entity
public class HardDiskDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private String driveInterface;       // SATA...

    private Float capacity;

    private String format;           // 3.5", 2.5"

    private Float powerConsumption;

    @ManyToOne
    @JoinColumn(name = "computer_id") // Имя колонки во внешнем ключе в БД
    private Computer computer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public Float getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(Float powerConsumption) {
        this.powerConsumption = powerConsumption;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}
