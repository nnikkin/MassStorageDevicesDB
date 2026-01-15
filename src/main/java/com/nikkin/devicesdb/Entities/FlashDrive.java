package com.nikkin.devicesdb.Entities;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class FlashDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Float capacity;
    private String usbInterface;
    private String usbType;
    private Float writeSpeed;
    private Float readSpeed;

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

    public String getUsbInterface() {
        return usbInterface;
    }

    public void setUsbInterface(String usbInterface) {
        this.usbInterface = usbInterface;
    }

    public String getUsbType() {
        return usbType;
    }

    public void setUsbType(String usbType) {
        this.usbType = usbType;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
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
}

