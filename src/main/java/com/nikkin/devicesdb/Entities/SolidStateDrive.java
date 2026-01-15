package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class SolidStateDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String driveInterface;
    
    private Float capacity;
    
    private String nandType;
    
    private Integer tbw;
    
    private Float writeSpeed;
    
    private Float readSpeed;
    
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

    public String getNandType() {
        return nandType;
    }

    public void setNandType(String nandType) {
        this.nandType = nandType;
    }


    public Integer getTbw() {
        return tbw;
    }

    public void setTbw(Integer tbw) {
        this.tbw = tbw;
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
}
