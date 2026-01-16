package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;

@Entity
public class FlashDrive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float capacity;

    private String usbInterface;

    private Float writeSpeed;

    private Float readSpeed;

    @ManyToOne
    @JoinColumn(name = "computer_id") // Имя колонки во внешнем ключе в БД
    private Computer computer;

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

    public Computer getComputer() {
        return computer;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
}

