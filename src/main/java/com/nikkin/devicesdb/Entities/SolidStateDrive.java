package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class SolidStateDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String driveInterface;       // SATA...

    @Column(nullable = false)
    @Positive
    private float capacity;

    private String nandType;        // SLC, MLC, TLC...

    @Positive
    private int tbwTb;

    @Positive
    private float writeSpeed;       // Kb/s

    @Positive
    private float readSpeed;

    @Positive
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

    public String getNandType() {
        return nandType;
    }

    public void setNandType(String nandType) {
        this.nandType = nandType;
    }

    public int getTbwTb() {
        return tbwTb;
    }

    public void setTbwTb(int tbwTb) {
        this.tbwTb = tbwTb;
    }

    public float getWriteSpeed() {
        return writeSpeed;
    }

    public void setWriteSpeed(float writeSpeed) {
        this.writeSpeed = writeSpeed;
    }

    public float getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(float readSpeed) {
        this.readSpeed = readSpeed;
    }

    public float getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(float powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
}
