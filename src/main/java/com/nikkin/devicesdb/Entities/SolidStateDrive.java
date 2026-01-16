package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;

@Entity
public class SolidStateDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String manufacturer;

    private String driveInterface;
    
    private Float capacity;
    
    private String nandType;

    private Float writeSpeed;
    
    private Float readSpeed;
    
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

    public String getNandType() {
        return nandType;
    }

    public void setNandType(String nandType) {
        this.nandType = nandType;
    }

    public Float getWriteSpeed() {
        return writeSpeed;
    }

    public void setWriteSpeed(Float writeSpeed) {
        this.writeSpeed = writeSpeed;
    }

    public Float getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(Float readSpeed) {
        this.readSpeed = readSpeed;
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
