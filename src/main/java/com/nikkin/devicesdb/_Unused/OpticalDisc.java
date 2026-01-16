package com.nikkin.devicesdb._Unused;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;

@Entity
public class OpticalDisc {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String type;

    private Float capacity;

    private Integer speedMultiplier;

    private String rewriteType;

    private Integer layers;

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

    @Nullable
    public String getType() {
        return type;
    }

    public void setType(@Nullable String type) {
        this.type = type;
    }

    public Float getCapacity() {
        return capacity;
    }

    public void setCapacity(Float capacity) {
        this.capacity = capacity;
    }

    @Nullable
    public Integer getSpeedMultiplier() {
        return speedMultiplier;
    }

    public void setSpeedMultiplier(@Nullable Integer speedMultiplier) {
        this.speedMultiplier = speedMultiplier;
    }

    public String getRewriteType() {
        return rewriteType;
    }

    public void setRewriteType(String rewriteType) {
        this.rewriteType = rewriteType;
    }

    @Nullable
    public Integer getLayers() {
        return layers;
    }

    public void setLayers(@Nullable Integer layers) {
        this.layers = layers;
    }
}
