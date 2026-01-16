package com.nikkin.devicesdb._Unused;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FloppyDisk {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Float capacity;

    private Float format;

    private String diskDensity;

    private Boolean isDoubleSided;

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

    public String getDiskDensity() {
        return diskDensity;
    }

    public void setDiskDensity(String diskDensity) {
        this.diskDensity = diskDensity;
    }

    public boolean isDoubleSided() {
        return isDoubleSided;
    }

    public void setDoubleSided(Boolean doubleSided) {
        isDoubleSided = doubleSided;
    }
}
