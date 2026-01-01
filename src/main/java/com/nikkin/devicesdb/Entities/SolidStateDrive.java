package com.nikkin.devicesdb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class SolidStateDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=30)
    private String name;

    @Size(min=1, max=10)
    private String driveInterface;       // SATA...
    
    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;
    
    @Size(max=10)
    private String nandType;        // SLC, MLC, TLC...

    @Nullable
    @Positive(message = "Максимальный ресурс записи не может быть отрицательным либо равным нулю")
    private Integer tbw;

    @Nullable
    @Positive(message = "Скорость чтения не может быть отрицательным либо равным нулю")
    private Float writeSpeed;       // Kb/s

    @Nullable
    @Positive(message = "Скорость записи не может быть отрицательным либо равным нулю")
    private Float readSpeed;

    @Nullable
    @Positive(message = "Значение энергопотребления не может быть отрицательным либо равным нулю")
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

    @Nullable
    public Integer getTbw() {
        return tbw;
    }

    public void setTbw(@Nullable Integer tbw) {
        this.tbw = tbw;
    }

    @Nullable
    public Float getWriteSpeed() {
        return writeSpeed;
    }

    public void setWriteSpeed(@Nullable Float writeSpeed) {
        this.writeSpeed = writeSpeed;
    }

    @Nullable
    public Float getReadSpeed() {
        return readSpeed;
    }

    public void setReadSpeed(@Nullable Float readSpeed) {
        this.readSpeed = readSpeed;
    }

    @Nullable
    public Float getPowerConsumption() {
        return powerConsumption;
    }

    public void setPowerConsumption(@Nullable Float powerConsumption) {
        this.powerConsumption = powerConsumption;
    }
}
