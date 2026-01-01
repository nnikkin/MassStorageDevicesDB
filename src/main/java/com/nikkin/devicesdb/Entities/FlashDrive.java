package com.nikkin.devicesdb.Entities;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
public class FlashDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 30)
    private String name;

    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;

    @Size(max = 10)
    private String usbInterface;    // Type-A, Type-C...

    @Size(max = 10)
    private String usbType;         // 1.0, 2.0, 3.0...

    @Positive(message = "Скорость записи не может быть отрицательной либо равной нулю")
    @Nullable
    private Float writeSpeed;

    @Positive(message = "Скорость чтения не может быть отрицательной либо равной нулю")
    @Nullable
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
}

