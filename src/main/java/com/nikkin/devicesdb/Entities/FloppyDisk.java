package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class FloppyDisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private float capacity;

    @Positive(message = "Формат не может быть отрицательным либо равным нулю")
    private float format;       // 8-inch, 5-inch, 3.5-inch...

    private char diskDensity;   // SD, DD, QD, HD...

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

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public float getFormat() {
        return format;
    }

    public void setFormat(float format) {
        this.format = format;
    }

    public char getDiskDensity() {
        return diskDensity;
    }

    public void setDiskDensity(char diskDensity) {
        this.diskDensity = diskDensity;
    }
}
