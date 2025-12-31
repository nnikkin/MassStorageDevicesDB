package com.nikkin.devicesdb.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
public class FloppyDisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max=30)
    private String name;

    @Positive(message = "Объём не может быть отрицательным либо равным нулю")
    private Float capacity;

    @Nullable
    @Positive(message = "Формат не может быть отрицательным либо равным нулю")
    private Float format;       // 8-inch, 5-inch, 3.5-inch...

    @Size(max=1)
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

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    public Float getFormat() {
        return format;
    }

    public void setFormat(Float format) {
        this.format = format;
    }

    public char getDiskDensity() {
        return diskDensity;
    }

    public void setDiskDensity(char diskDensity) {
        this.diskDensity = diskDensity;
    }
}
