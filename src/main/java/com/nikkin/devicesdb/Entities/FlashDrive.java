package com.nikkin.devicesdb.Entities;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class FlashDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @Column(nullable = false)
    private String usbInterface;    // Type-A, Type-C...

    private String usbType;         // 1.0, 2.0, 3.0...

    @Positive
    private float capacity;

    @Positive
    private float writeSpeed;

    @Positive
    private float readSpeed;

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

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
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
}

