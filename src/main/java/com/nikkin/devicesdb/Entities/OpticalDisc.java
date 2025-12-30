package com.nikkin.devicesdb.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class OpticalDisc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(nullable = false)
    private String type;            // CD, DVD...

    @Column(nullable = false)
    @Positive
    private float capacity;

    @Positive
    private int speedMultiplier;    // 2x, 4x...

    private String rewriteType;     // R, RW...

    @Positive
    private int layers;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getCapacity() {
        return capacity;
    }

    public void setCapacity(float capacity) {
        this.capacity = capacity;
    }

    public int getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(int speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public String getRewriteType() {
        return rewriteType;
    }

    public void setRewriteType(String rewriteType) {
        this.rewriteType = rewriteType;
    }

    public int getLayers() {
        return layers;
    }

    public void setLayers(int layers) {
        this.layers = layers;
    }
}
